
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) {
    fun getUserSettings(userId: String = "default_user"): Flow<UserSettings?> {
        return userSettingsDao.getUserSettings(userId)
    }

    suspend fun updateUserSettings(userSettings: UserSettings) {
        userSettingsDao.updateUserSettings(userSettings)
    }

    suspend fun insertUserSettings(userSettings: UserSettings) {
        userSettingsDao.insertUserSettings(userSettings)
    }

    suspend fun getCurrentSettings(userId: String = "default_user"): UserSettings {
        return getUserSettings(userId).first() ?: UserSettings(userId = userId)
    }
}
