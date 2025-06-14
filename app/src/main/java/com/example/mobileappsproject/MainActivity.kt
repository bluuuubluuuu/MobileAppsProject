package com.example.mobileappsproject

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mobileappsproject.data.AppDatabase
import com.example.mobileappsproject.data.DatabaseProvider
import com.example.mobileappsproject.navigation.AppNavGraph
import com.example.mobileappsproject.ui.theme.MobileAppsProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.getDatabase(applicationContext)

        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            val dao = db.employeeDao()
            val existingAdmin = dao.getEmployeeByEmail("admin@admin.com")
            if (existingAdmin == null) {
                dao.insertEmployee(
                    com.example.mobileappsproject.data.entities.Employee(
                        name = "Admin",
                        email = "admin@admin.com",
                        password = "admin123",
                        department = "Management",
                        designation = "Administrator",
                        leaveBalance = 999,
                        role = "admin"
                    )
                )
            }
        }

        setContent {
            MobileAppsProjectTheme {
                val navController = rememberNavController()
                // ✅ Pass both navController and db
                AppNavGraph(navController = navController, db = db)
            }
        }
    }
}