
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val userId: String = "",
    val roastLevel: RoastLevel = RoastLevel.MILD,
    val language: Language = Language.ENGLISH,
    val gender: Gender = Gender.OTHER,
    val notificationTime: String = "09:00", // HH:mm format
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val isOnboardingCompleted: Boolean = false,
    val dailyNotificationsEnabled: Boolean = true
)

enum class RoastLevel(val displayName: String) {
    MILD("Mild"),
    SPICY("Spicy"),
    EXTRA_SPICY("Extra Spicy")
}

enum class Language(val displayName: String, val code: String) {
    ENGLISH("English", "en"),
    HINDI("Hindi", "hi")
}

enum class Gender(val displayName: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

enum class ThemeMode(val displayName: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System")
}
