package com.example.mobileappsproject.employee.profileedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappsproject.data.dao.EmployeeDao
import com.example.mobileappsproject.data.entities.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val dao: EmployeeDao,
    private val original: Employee
) : ViewModel() {

    private val _name = MutableStateFlow(original.name)
    private val _email = MutableStateFlow(original.email)
    private val _department = MutableStateFlow(original.department)
    private val _designation = MutableStateFlow(original.designation)

    val name: StateFlow<String> get() = _name
    val email: StateFlow<String> get() = _email
    val department: StateFlow<String> get() = _department
    val designation: StateFlow<String> get() = _designation

    fun onNameChange(value: String) { _name.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onDepartmentChange(value: String) { _department.value = value }
    fun onDesignationChange(value: String) { _designation.value = value }

    private fun getUpdatedEmployee(): Employee {
        return original.copy(
            name = _name.value,
            email = _email.value,
            department = _department.value,
            designation = _designation.value
        )
    }

    fun saveProfile(onSaved: (Employee) -> Unit) {
        viewModelScope.launch {
            val updated = getUpdatedEmployee()
            dao.updateEmployee(updated)
            onSaved(updated)
        }
    }
}
