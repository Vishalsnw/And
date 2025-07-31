
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val userId: String = "default_user",
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val reminderFrequency: String = "daily",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
