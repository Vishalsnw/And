
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val goalId: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val dueDate: Date? = null,
    val priority: Priority = Priority.MEDIUM
)
package com.example.goalguru.data.model

import java.util.Date

data class Task(
    val id: String = "",
    val goalId: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: Date? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
