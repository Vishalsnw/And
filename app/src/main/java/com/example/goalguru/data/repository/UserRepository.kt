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
}
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
) {

    fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser()
    }

    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
