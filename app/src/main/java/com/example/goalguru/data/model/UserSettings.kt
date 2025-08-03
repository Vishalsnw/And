package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val userId: String = "default_user",
    val notificationsEnabled: Boolean = true,
    val dailyRemindersEnabled: Boolean = true,
    val reminderTime: String = "09:00",
    val darkModeEnabled: Boolean = false
)