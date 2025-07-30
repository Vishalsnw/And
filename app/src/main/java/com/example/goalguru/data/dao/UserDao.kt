
package com.example.goalguru.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getCurrentUser(): User?

    @Query("SELECT * FROM user_settings WHERE userId = :userId")
    fun getUserSettings(userId: String): Flow<UserSettings?>

    @Query("SELECT * FROM user_settings WHERE id = 'default'")
    fun getUserSettings(): Flow<UserSettings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(settings: UserSettings)

    @Update
    suspend fun updateUserSettings(settings: UserSettings)
}
