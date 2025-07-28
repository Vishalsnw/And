package com.example.goalguru.data.model

data class UserSettings(
    val userId: String = "",
    valnotificationsEnabled: Boolean = true,
    val dailyReminderTime: String = "09:00",
    val weeklyGoalReview: Boolean = true,
    val theme: Theme = Theme.SYSTEM,
    val language: String = "en"
)

enum class Theme {
    LIGHT, DARK, SYSTEM
}