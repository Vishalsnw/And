package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: String,
    val goalId: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val dueDate: Date?,
    val priority: Priority,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)
