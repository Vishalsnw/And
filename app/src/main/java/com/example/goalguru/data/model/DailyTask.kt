package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "daily_tasks")
data class DailyTask(
    @PrimaryKey
    val id: String,
    val goalId: String,
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val isCompleted: Boolean = false,
    val completedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)