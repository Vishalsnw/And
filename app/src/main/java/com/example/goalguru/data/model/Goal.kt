package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val targetDate: Date,
    val priority: Priority,
    val status: GoalStatus,
    val progress: Float = 0f,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val roadmap: List<String> = emptyList(),
    val aiGeneratedTasks: List<String> = emptyList()
)

enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}

enum class GoalStatus {
    ACTIVE, COMPLETED, PAUSED, ARCHIVED
}
package com.example.goalguru.data.model

import java.util.Date

data class Goal(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val priority: Priority = Priority.MEDIUM,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val targetDate: Date? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val progress: Float = 0.0f,
    val isCompleted: Boolean = false
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class GoalStatus {
    ACTIVE, COMPLETED, PAUSED, ARCHIVED
}
