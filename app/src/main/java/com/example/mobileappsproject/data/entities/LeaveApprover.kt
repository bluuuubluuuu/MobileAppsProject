package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeaveApprover(
    @PrimaryKey val approverID: Int,
    val approverName: String,
    val position: String,
    val email: String
)