
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.mappers.toUser
import com.example.goalguru.data.mappers.toUserEntity
import com.example.goalguru.data.mappers.toUserSettings
import com.example.goalguru.data.mappers.toUserSettingsEntity
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userSettingsDao: UserSettingsDao
) : UserRepository {

    override fun getUserById(userId: String): Flow<User?> = 
        userDao.getUserById(userId).map { it?.toUser() }

    override suspend fun getUserByEmail(email: String): User? = 
        userDao.getUserByEmail(email)?.toUser()

    override suspend fun insertUser(user: User) = 
        userDao.insertUser(user.toUserEntity())

    override suspend fun updateUser(user: User) = 
        userDao.updateUser(user.toUserEntity())

    override suspend fun deleteUser(user: User) = 
        userDao.deleteUser(user.toUserEntity())

    override fun getUserSettings(userId: String): Flow<UserSettings?> = 
        userSettingsDao.getUserSettings(userId).map { it?.toUserSettings() }

    override suspend fun insertUserSettings(settings: UserSettings) = 
        userSettingsDao.insertUserSettings(settings.toUserSettingsEntity())

    override suspend fun updateUserSettings(settings: UserSettings) = 
        userSettingsDao.updateUserSettings(settings.toUserSettingsEntity())
}
