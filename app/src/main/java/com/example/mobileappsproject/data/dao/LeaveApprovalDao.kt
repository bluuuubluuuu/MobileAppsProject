package com.example.mobileappsproject.data.dao

import androidx.room.*
import com.example.mobileappsproject.data.entities.LeaveApproval
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveApprovalDao {

    @Query("SELECT * FROM LeaveApproval")
    fun getAllApprovals(): Flow<List<LeaveApproval>>

    @Query("SELECT * FROM LeaveApproval WHERE approvalID = :id")
    suspend fun getApprovalById(id: Int): LeaveApproval?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApproval(approval: LeaveApproval)

    @Update
    suspend fun updateApproval(approval: LeaveApproval)

    @Delete
    suspend fun deleteApproval(approval: LeaveApproval)
}