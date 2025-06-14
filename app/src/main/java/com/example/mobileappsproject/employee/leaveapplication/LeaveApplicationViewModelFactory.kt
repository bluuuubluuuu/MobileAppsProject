package com.example.mobileappsproject.employee.leaveapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileappsproject.data.dao.EmployeeDao
import com.example.mobileappsproject.data.dao.LeaveApplicationDao

class LeaveApplicationViewModelFactory(
    private val leaveApplicationDao: LeaveApplicationDao,
    private val employeeDao: EmployeeDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaveApplicationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaveApplicationViewModel(leaveApplicationDao, employeeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
