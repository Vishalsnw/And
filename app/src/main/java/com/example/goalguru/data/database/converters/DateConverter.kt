
package com.example.goalguru.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let {
            LocalDateTime.parse(it, formatter)
        }
    }
}
package com.example.goalguru.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }
}
