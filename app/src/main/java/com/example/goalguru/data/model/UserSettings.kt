
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: Int = 1,
    val deepSeekApiKey: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: Gender = Gender.OTHER,
    val notificationsEnabled: Boolean = true,
    val notificationStyle: String = "Mild",
    val notificationTime: String = "21:00", // 9 PM IST
    val darkModeEnabled: Boolean = false,
    val language: String = "en"
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class MotivationStyle {
    MILD, MODERATE, HARSH
}
package com.example.goalguru.data.model

data class UserSettings(
    val name: String = "",
    val email: String = "",
    val notificationsEnabled: Boolean = true,
    val reminderFrequency: ReminderFrequency = ReminderFrequency.DAILY,
    val theme: AppTheme = AppTheme.SYSTEM
)

enum class ReminderFrequency {
    DAILY, WEEKLY, MONTHLY, NEVER
}

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}
