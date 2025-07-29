package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.UserSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUserSettings(): UserSettings? = userDao.getUserSettings()

    suspend fun saveUserSettings(userSettings: UserSettings) = userDao.insertUserSettings(userSettings)

    suspend fun updateUserSettings(userSettings: UserSettings) = userDao.updateUserSettings(userSettings)
}