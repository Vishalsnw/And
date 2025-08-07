
package com.example.goalguru.data.mappers

import com.example.goalguru.data.database.entities.UserEntity
import com.example.goalguru.data.database.entities.UserSettingsEntity
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isActive = isActive
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isActive = isActive
    )
}

fun UserSettingsEntity.toUserSettings(): UserSettings {
    return UserSettings(
        userId = userId,
        notificationsEnabled = notificationsEnabled,
        reminderTime = reminderTime ?: "09:00",
        theme = theme,
        language = language,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserSettings.toUserSettingsEntity(): UserSettingsEntity {
    return UserSettingsEntity(
        id = userId, // Using userId as id for entity
        userId = userId,
        notificationsEnabled = notificationsEnabled,
        reminderTime = reminderTime,
        theme = theme,
        language = language,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
