package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeaveType(
    @PrimaryKey val leaveTypeID: Int,
    val leaveTypeName: String,
    val description: String,
    val maxDays: Int,
    val requiredApproval: Boolean
)