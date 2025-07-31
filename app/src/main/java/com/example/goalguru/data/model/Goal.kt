package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goals")
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
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.util.Converters
import java.util.Date
import java.util.UUID

@Entity(tableName = "goals")
@TypeConverters(Converters::class)
data class Goal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val category: GoalCategory = GoalCategory.PERSONAL,
    val priority: GoalPriority = GoalPriority.MEDIUM,
    val targetDate: Date? = null,
    val isCompleted: Boolean = false,
    val progress: Float = 0.0f,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)

enum class GoalCategory {
    PERSONAL, PROFESSIONAL, HEALTH, EDUCATION, FINANCIAL, OTHER
}

enum class GoalPriority {
    LOW, MEDIUM, HIGH, URGENT
}
