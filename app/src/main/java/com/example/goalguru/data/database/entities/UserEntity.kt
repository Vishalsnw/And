
package com.example.goalguru.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.database.converters.DateConverter
import java.time.LocalDateTime

@Entity(tableName = "users")
@TypeConverters(DateConverter::class)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isActive: Boolean = true
)
