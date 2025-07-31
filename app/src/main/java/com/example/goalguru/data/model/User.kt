package com.example.goalguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goalguru.data.util.Converters
import java.util.Date

@Entity(tableName = "users")
@TypeConverters(Converters::class)
data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val gender: Gender = Gender.OTHER,
    val deepSeekApiKey: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class Gender {
    MALE, FEMALE, OTHER
}
