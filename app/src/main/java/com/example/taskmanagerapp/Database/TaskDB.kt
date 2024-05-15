package com.example.taskmanagerapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanagerapp.converters.TaskTypeConverter
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.taskdao.TaskInterface

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TaskTypeConverter::class)
abstract class TaskDB : RoomDatabase() {

    abstract fun taskDao(): TaskInterface

    companion object {
        @Volatile
        private var INSTANCE: TaskDB? = null

        fun getInstance(context: Context): TaskDB {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java, "task_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
