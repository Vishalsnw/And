package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUserSettings(): Flow<UserSettings?> = userDao.getUserSettings()

    suspend fun insertUserSettings(userSettings: UserSettings) = userDao.insertUserSettings(userSettings)

    suspend fun updateUserSettings(userSettings: UserSettings) = userDao.updateUserSettings(userSettings)
}