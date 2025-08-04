
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserById(userId: String): Flow<User?>
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    fun getUserSettings(userId: String): Flow<UserSettings?>
    suspend fun insertUserSettings(settings: UserSettings)
    suspend fun updateUserSettings(settings: UserSettings)
}
