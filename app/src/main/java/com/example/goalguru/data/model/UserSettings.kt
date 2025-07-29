package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: String,
    val userId: String,
    val notificationsEnabled: Boolean = true,
    val dailyReminderTime: String? = null,
    val weeklyReviewEnabled: Boolean = true,
    val theme: String = "SYSTEM",
    val language: String = "en",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)