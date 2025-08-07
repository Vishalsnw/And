package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.database.entities.UserSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(userSettings: UserSettingsEntity)

    @Update
    suspend fun updateUserSettings(userSettings: UserSettingsEntity)

    @Delete
    suspend fun deleteUserSettings(userSettings: UserSettingsEntity)

    @Query("SELECT * FROM user_settings WHERE userId = :userId")
    suspend fun getUserSettingsById(userId: String): UserSettingsEntity?

    @Query("SELECT * FROM user_settings")
    fun getAllUserSettings(): Flow<List<UserSettingsEntity>>
}