package com.example.mobileappsproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobileappsproject.data.dao.*
import com.example.mobileappsproject.data.entities.*

@Database(
    entities = [
        Employee::class,
        LeaveApproval::class,
        LeaveApprover::class,
        LeaveType::class,
        LeaveApplication::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun leaveApprovalDao(): LeaveApprovalDao
    abstract fun leaveApproverDao(): LeaveApproverDao
    abstract fun leaveTypeDao(): LeaveTypeDao
    abstract fun leaveApplicationDao(): LeaveApplicationDao
}