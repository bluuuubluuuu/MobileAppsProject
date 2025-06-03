package com.example.mobileappsproject.data.dao

import androidx.room.*
import com.example.mobileappsproject.data.entities.LeaveType
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveTypeDao {

    @Query("SELECT * FROM LeaveType")
    fun getAllLeaveTypes(): Flow<List<LeaveType>>

    @Query("SELECT * FROM LeaveType WHERE leaveTypeID = :id")
    suspend fun getLeaveTypeById(id: Int): LeaveType?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeaveType(leaveType: LeaveType)

    @Update
    suspend fun updateLeaveType(leaveType: LeaveType)

    @Delete
    suspend fun deleteLeaveType(leaveType: LeaveType)
}