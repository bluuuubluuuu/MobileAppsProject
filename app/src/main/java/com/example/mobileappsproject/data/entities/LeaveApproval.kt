package com.example.mobileappsproject.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = LeaveApplication::class, parentColumns = ["leaveID"], childColumns = ["leaveID"]),
        ForeignKey(entity = LeaveApprover::class, parentColumns = ["approverID"], childColumns = ["approverID"])
    ]
)
data class LeaveApproval(
    @PrimaryKey val approvalID: Int,
    val leaveID: Int,
    val approverID: Int,
    val approvalStatus: String,
    val comments: String,
    val timestamp: String
)