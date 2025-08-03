
package com.example.goalguru.data.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPriority(priority: com.example.goalguru.data.model.Goal.Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): com.example.goalguru.data.model.Goal.Priority {
        return com.example.goalguru.data.model.Goal.Priority.valueOf(priority)
    }
}
