package com.example.mobileappsproject.data.dao

import androidx.room.*
import com.example.mobileappsproject.data.entities.LeaveApprover
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveApproverDao {

    @Query("SELECT * FROM LeaveApprover")
    fun getAllApprovers(): Flow<List<LeaveApprover>>

    @Query("SELECT * FROM LeaveApprover WHERE approverID = :id")
    suspend fun getApproverById(id: Int): LeaveApprover?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApprover(approver: LeaveApprover)

    @Update
    suspend fun updateApprover(approver: LeaveApprover)

    @Delete
    suspend fun deleteApprover(approver: LeaveApprover)
}