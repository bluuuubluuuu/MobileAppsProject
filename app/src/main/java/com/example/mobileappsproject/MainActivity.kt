package com.example.mobileappsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.mobileappsproject.data.DatabaseProvider
import com.example.mobileappsproject.data.entities.Employee
import com.example.mobileappsproject.ui.theme.MobileAppsProjectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            // This call creates (or opens) the database file on the device
            val db = DatabaseProvider.getDatabase(applicationContext)

            // Optionally perform a simple operation to trigger creation
            // For example, insert a dummy employee or just call any DAO method

            // If you don't want to insert, just call a query (which triggers creation)
            db.employeeDao().getAllEmployees()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileAppsProjectTheme {
        Greeting("Android")
    }
}