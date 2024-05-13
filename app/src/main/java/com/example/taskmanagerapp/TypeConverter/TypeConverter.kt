package com.example.taskmanagerapp.TypeConverter

import androidx.room.TypeConverter
import java.util.Date

class TypeConverter {
        @TypeConverter
        fun fromTimeStamps(value: Long):Date{
            return Date(value)
        }
    @TypeConverter
    fun dateTimeStamps(date: Date):Long{
        return date.time
    }
}