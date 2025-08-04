
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

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(id: String): User? {
        return userDao.getUser(id)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun getCurrentUser(): User? {
        // Implementation depends on your auth logic
        return null
    }

    override fun getUserSettings(userId: String): Flow<UserSettings?> {
        return userSettingsDao.getUserSettings(userId)
    }

    override suspend fun updateUserSettings(settings: UserSettings) {
        userSettingsDao.updateUserSettings(settings)
    }
}
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.config.FirebaseConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    
    override fun getUser(userId: String): Flow<User?> {
        return userDao.getUser(userId)
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun updateUserSettings(userSettings: UserSettings) {
        userDao.updateUserSettings(userSettings)
    }

    override fun getUserSettings(userId: String): Flow<UserSettings?> {
        return userDao.getUserSettings(userId)
    }

    override suspend fun getCurrentUser(): User? {
        return FirebaseConfig.auth.currentUser?.let { firebaseUser ->
            userDao.getUserById(firebaseUser.uid)
        }
    }

    override fun getCurrentUserFromDb(): Flow<User?> {
        return FirebaseConfig.auth.currentUser?.let { firebaseUser ->
            userDao.getUser(firebaseUser.uid)
        } ?: flowOf(null)
    }
}
