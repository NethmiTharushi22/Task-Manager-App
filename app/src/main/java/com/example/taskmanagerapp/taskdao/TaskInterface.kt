package com.example.taskmanagerapp.taskdao

import androidx.room.*
import com.example.taskmanagerapp.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskInterface {

    @Query("SELECT * FROM Task ORDER BY date DESC")
    fun showTaskList() : Flow<List<Task>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long

    @Delete
    suspend fun deleteTask(task: Task):Int

    @Query("DELETE FROM task WHERE taskId == taskid")
    suspend fun deleteTaskUsingId(taskId:String)
}