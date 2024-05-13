package com.example.taskmanagerapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="taskid")
    val id :String,
    @ColumnInfo(name = "Task Title")
    val topic:String,
    val content:String,
    val date: Date
){


}
