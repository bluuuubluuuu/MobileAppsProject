package com.example.mobileappsproject.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobileappsproject.data.entities.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "leave_db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Insert admin user when DB is first created
                        val dao = getDatabase(context).employeeDao()
                        kotlinx.coroutines.runBlocking {
                            dao.insertEmployee(
                                Employee(
                                    name = "Admin",
                                    email = "admin@admin.com",
                                    password = "admin123",
                                    department = "Management",
                                    designation = "Administrator",
                                    leaveBalance = 999,
                                    role = "admin"
                                )
                            )
                        }
                    }
                })
                .build()
            INSTANCE = instance
            instance
        }
    }
}
