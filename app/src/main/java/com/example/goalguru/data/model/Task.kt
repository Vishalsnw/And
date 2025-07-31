package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.util.Converters
import java.util.Date
import java.util.UUID

@Entity(tableName = "tasks")
@TypeConverters(Converters::class)
data class Task(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val goalId: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: Date? = null,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)
package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.util.Converters
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = Goal::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(Converters::class)
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val goalId: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: Date? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)
