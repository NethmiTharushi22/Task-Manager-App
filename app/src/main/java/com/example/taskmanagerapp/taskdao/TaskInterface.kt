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


    @Query("DELETE FROM Task WHERE taskid = :taskid")
    suspend fun deleteTaskUsingId(taskid: String): Int

  @Update
    suspend fun updateTask(task: Task):Int


//    @Query("UPDATE Task SET taskTitle =:topic ,content=:content WHERE  taskid =:taskid")
//    suspend fun updatetaskById(taskid: String,topic :String,content:String):Int

}