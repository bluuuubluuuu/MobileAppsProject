package com.example.mobileappsproject.employee.leaveapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappsproject.data.dao.EmployeeDao
import com.example.mobileappsproject.data.dao.LeaveApplicationDao
import com.example.mobileappsproject.data.entities.LeaveApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeaveApplicationViewModel(
    private val leaveApplicationDao: LeaveApplicationDao,
    private val employeeDao: EmployeeDao
) : ViewModel() {

    private val _leaveApplications = MutableStateFlow<List<LeaveApplication>>(emptyList())
    val leaveApplications: StateFlow<List<LeaveApplication>> = _leaveApplications

    private val _employeeId = MutableStateFlow("")
    val employeeId: StateFlow<String> = _employeeId

    init {
        refreshApplications()
    }

    fun fetchEmployeeIdByEmail(email: String) {
        viewModelScope.launch {
            val employee = employeeDao.getEmployeeByEmail(email)
            _employeeId.value = employee?.employeeID?.toString() ?: ""
        }
    }

    fun insertLeaveApplication(application: LeaveApplication) {
        viewModelScope.launch {
            leaveApplicationDao.insertLeaveApplication(application)
            refreshApplications()
        }
    }

    fun deleteLeaveApplication(application: LeaveApplication) {
        viewModelScope.launch {
            leaveApplicationDao.deleteLeaveApplication(application)
            refreshApplications()
        }
    }

    private fun refreshApplications() {
        viewModelScope.launch {
            _leaveApplications.value = leaveApplicationDao.getAllLeaveApplications()
        }
    }
}