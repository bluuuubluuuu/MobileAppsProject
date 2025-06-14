package com.example.mobileappsproject.admin.leavetype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappsproject.data.dao.LeaveTypeDao
import com.example.mobileappsproject.data.entities.LeaveType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LeaveTypeViewModel(private val dao: LeaveTypeDao) : ViewModel() {

    val leaveTypes = dao.getAllLeaveTypes()
        .map { it.sortedBy { type -> type.leaveTypeID } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insertLeaveType(leaveType: LeaveType) {
        viewModelScope.launch {
            dao.insertLeaveType(leaveType)
        }
    }

    fun updateLeaveType(leaveType: LeaveType) {
        viewModelScope.launch {
            dao.updateLeaveType(leaveType)
        }
    }

    fun deleteLeaveType(leaveType: LeaveType) {
        viewModelScope.launch {
            dao.deleteLeaveType(leaveType)
        }
    }
}