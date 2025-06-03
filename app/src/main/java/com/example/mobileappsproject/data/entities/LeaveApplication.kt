package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = Employee::class, parentColumns = ["employeeID"], childColumns = ["employeeID"]),
        ForeignKey(entity = LeaveType::class, parentColumns = ["leaveTypeID"], childColumns = ["leaveTypeID"])
    ]
)
data class LeaveApplication(
    @PrimaryKey val leaveID: Int,
    val employeeID: Int,
    val leaveTypeID: Int,
    val startDate: String,
    val endDate: String,
    val reason: String,
    val status: String
)