package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
) {

    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)

    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    fun getUserSettings(userId: String): Flow<UserSettings?> = userDao.getUserSettings(userId)

    suspend fun insertUserSettings(settings: UserSettings) = userDao.insertUserSettings(settings)

    suspend fun updateUserSettings(settings: UserSettings) = userDao.updateUserSettings(settings)

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun getUserSettings(): Flow<UserSettings> = userDao.getUserSettings()

    suspend fun saveUserSettings(settings: UserSettings) = userDao.insertUserSettings(settings)
}