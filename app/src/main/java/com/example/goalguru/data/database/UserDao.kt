
package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<User?>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("UPDATE users SET lastLoginAt = :lastLoginAt WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, lastLoginAt: Long)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET name = :name WHERE id = :userId")
    suspend fun updateUserName(userId: String, name: String)

    @Query("UPDATE users SET profileImageUrl = :imageUrl WHERE id = :userId")
    suspend fun updateUserProfileImage(userId: String, imageUrl: String?)
}
