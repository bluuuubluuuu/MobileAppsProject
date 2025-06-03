package com.example.mobileappsproject.data.dao

import androidx.room.*
import com.example.mobileappsproject.data.entities.LeaveApplication

@Dao
interface LeaveApplicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeaveApplication(application: LeaveApplication)

    @Update
    suspend fun updateLeaveApplication(application: LeaveApplication)

    @Delete
    suspend fun deleteLeaveApplication(application: LeaveApplication)

    @Query("SELECT * FROM LeaveApplication")
    suspend fun getAllLeaveApplications(): List<LeaveApplication>

    @Query("SELECT * FROM LeaveApplication WHERE employeeID = :employeeId")
    suspend fun getLeaveApplicationsByEmployee(employeeId: Int): List<LeaveApplication>

    @Query("SELECT * FROM LeaveApplication WHERE status = :status")
    suspend fun getLeaveApplicationsByStatus(status: String): List<LeaveApplication>
}