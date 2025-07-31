package com.example.goalguru.data.util

import androidx.room.TypeConverter
import com.example.goalguru.data.model.Gender
import com.example.goalguru.data.model.GoalCategory
import com.example.goalguru.data.model.GoalPriority
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromStringList(value: String?): List<String> {
        return if (value.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
        }
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        return gson.toJson(list ?: emptyList())
    }

    @TypeConverter
    fun fromGoalCategory(value: String): GoalCategory = GoalCategory.valueOf(value)

    @TypeConverter
    fun goalCategoryToString(category: GoalCategory): String = category.name

    @TypeConverter
    fun fromGoalPriority(value: String): GoalPriority = GoalPriority.valueOf(value)

    @TypeConverter
    fun goalPriorityToString(priority: GoalPriority): String = priority.name

    @TypeConverter
    fun fromGender(value: String): Gender = Gender.valueOf(value)

    @TypeConverter
    fun genderToString(gender: Gender): String = gender.name
}

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
