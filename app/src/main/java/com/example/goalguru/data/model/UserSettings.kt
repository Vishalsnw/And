
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val userId: String,
    val notificationsEnabled: Boolean = true,
    val reminderTime: String = "09:00",
    val theme: String = "SYSTEM",
    val language: String = "en",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
