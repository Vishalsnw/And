
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}

@Entity(tableName = "daily_tasks")
data class DailyTask(
    @PrimaryKey
    val id: String,
    val goalId: String,
    val title: String,
    val description: String,
    val dueDate: Date,
    val isCompleted: Boolean = false,
    val priority: Priority,
    val estimatedDuration: Int, // in minutes
    val completedAt: Date? = null,
    val createdAt: Date = Date(),
)
