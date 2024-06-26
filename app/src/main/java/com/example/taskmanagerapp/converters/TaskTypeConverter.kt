package com.example.taskmanagerapp.converters

import androidx.room.TypeConverter
import java.util.Date

class TaskTypeConverter {
    @TypeConverter
    fun fromTimeStamps(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateTimeStamps(date: Date): Long {
        return date.time
    }
}
