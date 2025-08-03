
package com.example.goalguru.data.util

import androidx.room.TypeConverter
import com.example.goalguru.data.model.Goal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let {
            LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromPriority(priority: Goal.Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Goal.Priority {
        return Goal.Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromStatus(status: Goal.Status): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(status: String): Goal.Status {
        return Goal.Status.valueOf(status)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}
