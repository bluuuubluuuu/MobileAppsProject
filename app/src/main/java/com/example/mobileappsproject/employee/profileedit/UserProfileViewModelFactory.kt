package com.example.mobileappsproject.employee.profileedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileappsproject.data.dao.EmployeeDao
import com.example.mobileappsproject.data.entities.Employee

class UserProfileViewModelFactory(
    private val employeeDao: EmployeeDao,
    private val employee: Employee
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(employeeDao, employee) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
