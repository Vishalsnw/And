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
    val description: String = "",
    val isCompleted: Boolean = false,
    val completed: Boolean = false, // Keep both for compatibility
    val dueDate: Date? = null,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val completed: Boolean = false,
    val goalId: String,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null
)
