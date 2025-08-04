package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val targetDays: Int,
    val currentProgress: Float = 0f,
    val status: Status = Status.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val roadmap: List<String> = emptyList(),
    val completedAt: LocalDateTime? = null
) {
    enum class Status {
        ACTIVE, COMPLETED, PAUSED, CANCELLED
    }
}