package com.example.mobileappsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.mobileappsproject.data.AppDatabase
import com.example.mobileappsproject.leavetype.LeaveTypeScreen
import com.example.mobileappsproject.leavetype.LeaveTypeViewModel
import com.example.mobileappsproject.ui.theme.MobileAppsProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room Database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "leave_database"
        ).build()

        val dao = db.leaveTypeDao()

        setContent {
            MobileAppsProjectTheme {
                // Provide ViewModel manually
                val viewModel: LeaveTypeViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return LeaveTypeViewModel(dao) as T
                        }
                    }
                )

                LeaveTypeScreen(viewModel = viewModel)
            }
        }
    }
}