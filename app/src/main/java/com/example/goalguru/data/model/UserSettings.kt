
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: String = "default",
    val notificationsEnabled: Boolean = true,
    val dailyReminderTime: String = "09:00",
    val weeklyReportEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val aiSuggestionsEnabled: Boolean = true,
)
