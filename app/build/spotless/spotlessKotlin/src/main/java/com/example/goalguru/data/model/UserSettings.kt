package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: Int = 1,
    val userName: String,
    val age: Int,
    val gender: Gender,
    val notificationEnabled: Boolean = true,
    val notificationTime: String = "21:00", // 9 PM IST
    val motivationStyle: MotivationStyle = MotivationStyle.MILD,
    val darkModeEnabled: Boolean = false,
    val language: String = "en",
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class MotivationStyle {
    MILD, MODERATE, HARSH
}
