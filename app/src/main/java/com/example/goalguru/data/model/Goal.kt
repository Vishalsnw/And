
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
    val roadmap: String,
    val targetDate: Date,
    val createdAt: Date = Date(),
    val progress: Float = 0.0f,
    val status: Status = Status.NOT_STARTED,
    val estimatedDuration: Int = 30, // in days
    val priority: Priority = Priority.MEDIUM
) {
    enum class Status {
        NOT_STARTED,
        IN_PROGRESS, 
        COMPLETED,
        PAUSED
    }
    
    enum class Priority {
        LOW,
        MEDIUM,
        HIGH
    }
}
