package com.example.goalguru.data.repository

import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.firebase.FirebaseConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUser(userId: String): Flow<User?> {
        return userDao.getUser(userId)
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

    suspend fun updateUserSettings(userSettings: UserSettings) {
        userDao.updateUserSettings(userSettings)
    }

    fun getUserSettings(userId: String): Flow<UserSettings?> {
        return userDao.getUserSettings(userId)
    }

    suspend fun getCurrentUser(): User? {
        return FirebaseConfig.auth.currentUser?.let { firebaseUser ->
            userDao.getUserById(firebaseUser.uid)
        }
    }

    fun getCurrentUserFromDb(): Flow<User?> {
        return FirebaseConfig.auth.currentUser?.let { firebaseUser ->
            userDao.getUserByIdFlow(firebaseUser.uid)
        } ?: flowOf(null)
    }
}