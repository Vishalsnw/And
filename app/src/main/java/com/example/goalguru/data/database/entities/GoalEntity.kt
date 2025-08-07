
package com.example.goalguru.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.database.converters.DateConverter
import com.example.goalguru.data.database.converters.ListStringConverter
import java.time.LocalDateTime

@Entity(tableName = "goals")
@TypeConverters(DateConverter::class, ListStringConverter::class)
data class GoalEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val targetDays: Int,
    val currentProgress: Float = 0f,
    val status: String = "ACTIVE",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val roadmap: List<String> = emptyList(),
    val completedAt: LocalDateTime? = null,
    val priority: String = "MEDIUM",
    val progress: Float = 0f,
    val estimatedDuration: Int = 30
)
