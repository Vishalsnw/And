
package com.example.goalguru.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.database.converters.DateConverter
import java.time.LocalDateTime

@Entity(tableName = "user_settings")
@TypeConverters(DateConverter::class)
data class UserSettingsEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val notificationsEnabled: Boolean = true,
    val reminderTime: String? = null, // Format: "HH:mm"
    val theme: String = "SYSTEM", // LIGHT, DARK, SYSTEM
    val language: String = "en",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
