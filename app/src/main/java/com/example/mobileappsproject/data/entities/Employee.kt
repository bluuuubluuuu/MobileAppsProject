package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey val employeeID: Int,
    val name: String,
    val department: String,
    val designation: String,
    val email: String,
    val leaveBalance: Int
)