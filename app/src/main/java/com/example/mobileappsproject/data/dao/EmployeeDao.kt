package com.example.mobileappsproject.data.dao

import androidx.room.*
import com.example.mobileappsproject.data.entities.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee WHERE email = :email LIMIT 1")
    suspend fun getEmployeeByEmail(email: String): Employee?

    @Query("SELECT * FROM Employee")
    suspend fun getAllEmployees(): List<Employee>

    @Query("SELECT * FROM Employee WHERE employeeID = :id")
    suspend fun getEmployeeById(id: Int): Employee?

    @Query("SELECT * FROM Employee WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): Employee?
}
