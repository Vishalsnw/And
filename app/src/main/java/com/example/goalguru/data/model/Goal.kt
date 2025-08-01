package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val category: GoalCategory,
    val priority: GoalPriority,
    val targetDate: Date,
    val createdAt: Date = Date(),
    val progress: Float = 0f,
    val isCompleted: Boolean = false,
    val userId: String
)

enum class GoalCategory {
    HEALTH, CAREER, PERSONAL, EDUCATION, FINANCE, RELATIONSHIPS, HOBBIES
}

enum class GoalPriority {
    LOW, MEDIUM, HIGH, URGENT
}
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
    val priority: Priority = Priority.MEDIUM,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val targetDate: Date?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val tags: List<String> = emptyList()
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class GoalStatus {
    ACTIVE, COMPLETED, PAUSED, CANCELLED
}
