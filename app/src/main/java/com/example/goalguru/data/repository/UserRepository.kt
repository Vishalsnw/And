package com.example.goalguru.data.repository

import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {

    private var userSettings = UserSettings()

    fun getUserSettings(): Flow<UserSettings> {
        return flowOf(userSettings)
    }

    suspend fun updateUserSettings(settings: UserSettings) {
        userSettings = settings
    }
}