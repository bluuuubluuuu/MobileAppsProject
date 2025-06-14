package com.example.mobileappsproject.admin.usermanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappsproject.data.dao.EmployeeDao
import com.example.mobileappsproject.data.entities.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserManagementViewModel(private val employeeDao: EmployeeDao) : ViewModel() {

    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees

    init {
        loadEmployees()
    }

    fun loadEmployees() {
        viewModelScope.launch {
            _employees.value = employeeDao.getAllEmployees()
        }
    }

    fun deleteEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeDao.deleteEmployee(employee)
            loadEmployees()
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeDao.updateEmployee(employee)
            loadEmployees()
        }
    }
}