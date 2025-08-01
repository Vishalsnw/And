package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: Priority,
    val status: Status,
    val targetDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val progress: Float = 0f
) {
    enum class Priority {
        LOW, MEDIUM, HIGH, URGENT
    }

    enum class Status {
        NOT_STARTED, IN_PROGRESS, COMPLETED, PAUSED, CANCELLED
    }
}