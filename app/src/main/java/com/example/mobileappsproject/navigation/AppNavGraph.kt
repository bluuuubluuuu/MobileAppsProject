package com.example.mobileappsproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileappsproject.admin.AdminDashboardScreen
import com.example.mobileappsproject.authentication.LoginScreen
import com.example.mobileappsproject.authentication.RegisterScreen
import com.example.mobileappsproject.data.AppDatabase
import com.example.mobileappsproject.admin.leavetype.LeaveTypeViewModel
import com.example.mobileappsproject.employee.leaveapplication.LeaveApplicationViewModel
import com.example.mobileappsproject.employee.leaveapplication.LeaveApplicationScreen
import com.example.mobileappsproject.ui.screens.MainScreen
import com.example.mobileappsproject.admin.leavetype.LeaveTypeScreen
import com.example.mobileappsproject.admin.usermanagement.UserManagementScreen
import com.example.mobileappsproject.admin.usermanagement.UserManagementViewModel
import com.example.mobileappsproject.employee.EmployeeDashboardScreen
import com.example.mobileappsproject.employee.profileedit.SessionViewModel
import com.example.mobileappsproject.employee.profileedit.UserProfileScreen
import com.example.mobileappsproject.employee.profileedit.UserProfileViewModel
import com.example.mobileappsproject.employee.profileedit.UserProfileViewModelFactory

@Composable
fun AppNavGraph(navController: NavHostController, db: AppDatabase) {

    val employeeDao = db.employeeDao()
    val sessionViewModel: SessionViewModel = viewModel() // Shared session

    NavHost(navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                employeeDao = employeeDao,
                navController = navController,
                onLoginSuccess = { employee ->
                    sessionViewModel.setEmployee(employee) // ✅ Save user session

                    if (employee.role == "admin") {
                        navController.navigate("adminDashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("employeeDashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("profile") {
            val employee = sessionViewModel.loggedInEmployee.collectAsState().value
            if (employee != null) {
                val viewModel: UserProfileViewModel = viewModel(
                    factory = UserProfileViewModelFactory(db.employeeDao(), employee)
                )

                UserProfileScreen(
                    viewModel = viewModel,
                    originalEmployee = employee,
                    onSave = { updatedEmployee ->
                        sessionViewModel.setEmployee(updatedEmployee)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("register") {
            RegisterScreen(employeeDao = employeeDao, navController = navController)
        }

        composable("main") {
            MainScreen(navController)
        }

        composable("adminDashboard") {
            AdminDashboardScreen(navController = navController)
        }

        composable("employeeDashboard") {
            EmployeeDashboardScreen(navController = navController)
        }

        composable("leaveType") {
            val dao = db.leaveTypeDao()
            val viewModel: LeaveTypeViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LeaveTypeViewModel(dao) as T
                }
            })
            LeaveTypeScreen(viewModel = viewModel, navController = navController)
        }

        composable("leaveApplication") {
            val leaveDao = db.leaveApplicationDao()
            val employeeDao = db.employeeDao()
            val viewModel: LeaveApplicationViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LeaveApplicationViewModel(leaveDao, employeeDao) as T
                }
            })
            LeaveApplicationScreen(viewModel = viewModel, navController = navController)
        }

        composable("userManagement") {
            val dao = db.employeeDao()
            val viewModel: UserManagementViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserManagementViewModel(dao) as T
                }
            })
            UserManagementScreen(viewModel = viewModel, navController = navController)
        }



        composable("leaveApproval") {
            // LeaveApprovalScreen()
        }
    }

}
