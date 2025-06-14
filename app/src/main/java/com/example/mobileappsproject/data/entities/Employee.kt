package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true) val employeeID: Int = 0,
    val name: String,
    val department: String,
    val designation: String,
    val email: String,
    val password: String,
    val leaveBalance: Int,
    val role: String = "user"
)