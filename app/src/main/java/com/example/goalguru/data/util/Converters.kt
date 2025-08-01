package com.example.goalguru.data.util

import androidx.room.TypeConverter
import com.example.goalguru.data.model.Gender
import com.example.goalguru.data.model.GoalCategory
import com.example.goalguru.data.model.GoalPriority
import java.util.Date

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
    fun fromGender(gender: Gender): String {
        return gender.name
    }

    @TypeConverter
    fun toGender(gender: String): Gender {
        return Gender.valueOf(gender)
    }

    @TypeConverter
    fun fromGoalCategory(category: GoalCategory): String {
        return category.name
    }

    @TypeConverter
    fun toGoalCategory(category: String): GoalCategory {
        return GoalCategory.valueOf(category)
    }

    @TypeConverter
    fun fromGoalPriority(priority: GoalPriority): String {
        return priority.name
    }

    @TypeConverter
    fun toGoalPriority(priority: String): GoalPriority {
        return GoalPriority.valueOf(priority)
    }
}
package com.example.goalguru.data.util

import androidx.room.TypeConverter
import com.example.goalguru.data.model.GoalStatus
import com.example.goalguru.data.model.Priority
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

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
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromGoalStatus(status: GoalStatus): String {
        return status.name
    }

    @TypeConverter
    fun toGoalStatus(status: String): GoalStatus {
        return GoalStatus.valueOf(status)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
