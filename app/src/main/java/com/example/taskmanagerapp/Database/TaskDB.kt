package com.example.taskmanagerapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.taskmanagerapp.models.Task
import kotlin.concurrent.Volatile

@Database(
    entities = [Task::class],
    version = 1
)
@TypeConverters(
TypeConverter::class
)
abstract class TaskDB:RoomDatabase() {

    companion object {

    @Volatile
    private var INSTANCE: TaskDB? = null
        fun getInstance(context: Context):TaskDB{
            synchronized(this){
                return INSTANCE ?:Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java,"task_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }


}