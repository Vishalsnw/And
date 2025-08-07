
package com.example.goalguru.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.database.converters.DateConverter
import java.time.LocalDateTime

@Entity(tableName = "daily_tasks")
@TypeConverters(DateConverter::class)
data class TaskEntity(
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
package com.example.goalguru.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.database.converters.DateConverter
import java.time.LocalDateTime

@Entity(tableName = "tasks")
@TypeConverters(DateConverter::class)
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val goalId: String,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val completedAt: LocalDateTime? = null,
    val dueDate: LocalDateTime? = null,
    val priority: String = "MEDIUM",
    val estimatedMinutes: Int = 30
)
