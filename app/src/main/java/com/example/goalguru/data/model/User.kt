package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val displayName: String,
    val profilePictureUrl: String? = null,
    val createdAt: Date = Date(),
    val lastLoginAt: Date = Date()
)



enum class Gender {
    MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
}