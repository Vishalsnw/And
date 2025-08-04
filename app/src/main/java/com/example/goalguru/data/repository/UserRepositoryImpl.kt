package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userSettingsDao: UserSettingsDao
) : UserRepository {

    override fun getUserById(userId: String): Flow<User?> = userDao.getUserById(userId)

    override suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)

    override suspend fun insertUser(user: User) = userDao.insertUser(user)

    override suspend fun updateUser(user: User) = userDao.updateUser(user)

    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    override fun getUserSettings(userId: String): Flow<UserSettings?> = 
        userSettingsDao.getUserSettings(userId)

    override suspend fun insertUserSettings(settings: UserSettings) = 
        userSettingsDao.insertUserSettings(settings)

    override suspend fun updateUserSettings(settings: UserSettings) = 
        userSettingsDao.updateUserSettings(settings)
}