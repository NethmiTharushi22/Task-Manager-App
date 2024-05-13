package com.example.taskmanagerapp.taskdao

import androidx.room.*
import com.example.taskmanagerapp.models.Task

@Dao
interface TaskInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long
}