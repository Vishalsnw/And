
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.util.*

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    @DocumentId
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val targetDate: Date = Date(),
    val createdAt: Date = Date(),
    val isCompleted: Boolean = false,
    val userId: String = "",
    val roadmapGenerated: Boolean = false
)

@Entity(tableName = "daily_tasks")
data class DailyTask(
    @PrimaryKey
    @DocumentId
    val id: String = UUID.randomUUID().toString(),
    val goalId: String = "",
    val title: String = "",
    val description: String = "",
    val dayNumber: Int = 0,
    val scheduledDate: Date = Date(),
    val isCompleted: Boolean = false,
    val isSkipped: Boolean = false,
    val completedAt: Date? = null,
    val userId: String = ""
)

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey
    @DocumentId
    val id: String = UUID.randomUUID().toString(),
    val goalId: String = "",
    val userId: String = "",
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val completionPercentage: Float = 0f,
    val lastUpdated: Date = Date()
)
