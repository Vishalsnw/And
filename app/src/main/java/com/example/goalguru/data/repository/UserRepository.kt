package com.example.goalguru.data.repository

import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(userId: String): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getCurrentUser(): User?
    fun getCurrentUserFromDb(): Flow<User?>
    fun getUserSettings(userId: String): Flow<UserSettings?>
    suspend fun updateUserSettings(userSettings: UserSettings)
}