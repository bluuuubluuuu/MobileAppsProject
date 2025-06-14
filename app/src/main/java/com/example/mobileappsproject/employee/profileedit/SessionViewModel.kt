package com.example.mobileappsproject.employee.profileedit

import androidx.lifecycle.ViewModel
import com.example.mobileappsproject.data.entities.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel : ViewModel() {
    private val _loggedInEmployee = MutableStateFlow<Employee?>(null)
    val loggedInEmployee: StateFlow<Employee?> get() = _loggedInEmployee

    fun setEmployee(employee: Employee) {
        _loggedInEmployee.value = employee
    }

    fun clearSession() {
        _loggedInEmployee.value = null
    }
}