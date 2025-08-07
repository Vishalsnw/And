
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.mappers.toUserSettings
import com.example.goalguru.data.mappers.toUserSettingsEntity
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) {
    fun getUserSettings(userId: String = "default_user"): Flow<UserSettings?> {
        return userSettingsDao.getUserSettings(userId).map { it?.toUserSettings() }
    }

    suspend fun updateUserSettings(userSettings: UserSettings) {
        userSettingsDao.updateUserSettings(userSettings.toUserSettingsEntity())
    }

    suspend fun insertUserSettings(userSettings: UserSettings) {
        userSettingsDao.insertUserSettings(userSettings.toUserSettingsEntity())
    }

    suspend fun getCurrentSettings(userId: String = "default_user"): UserSettings {
        return getUserSettings(userId).first() ?: UserSettings(userId = userId)
    }
}
