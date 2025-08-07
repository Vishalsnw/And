
package com.example.goalguru.data.model

import java.time.LocalDateTime

data class UserSettings(
    val userId: String,
    val notificationsEnabled: Boolean = true,
    val reminderTime: String = "09:00",
    val theme: String = "SYSTEM",
    val language: String = "en",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
