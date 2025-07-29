package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)

enum class Gender {
    MALE, FEMALE, OTHER
}