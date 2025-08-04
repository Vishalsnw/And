package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val roadmap: String = "",
    val targetDate: Date,
    val priority: Priority = Priority.MEDIUM,
    val estimatedDuration: Int, // in days
    val createdAt: Date = Date(),
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val progress: Float = 0.0f
) {
    enum class Priority {
        LOW, MEDIUM, HIGH
    }
}
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
    val category: String,
    val targetDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val priority: Priority,
    val status: Status,
    val progress: Float = 0f,
    val roadmap: List<String> = emptyList(),
    val estimatedDuration: Int = 30
) {
    enum class Priority { LOW, MEDIUM, HIGH }
    enum class Status { ACTIVE, COMPLETED, PAUSED, CANCELLED }
}
