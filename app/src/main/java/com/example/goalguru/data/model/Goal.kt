package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.util.Converters
import java.util.Date

@Entity(tableName = "goals")
@TypeConverters(Converters::class)
data class Goal(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String,
    val targetDate: Date?,
    val priority: Priority,
    val status: GoalStatus,
    val progress: Float = 0f,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val roadmap: List<String> = emptyList(),
    val aiGeneratedTasks: List<String> = emptyList(),
    val isCompleted: Boolean = false,
)

enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}

enum class GoalStatus {
    ACTIVE, COMPLETED, PAUSED, ARCHIVED
}
