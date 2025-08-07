
package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE userId = :userId")
    fun getUserSettings(userId: String): Flow<UserSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(settings: UserSettings)

    @Update
    suspend fun updateUserSettings(settings: UserSettings)

    @Delete
    suspend fun deleteUserSettings(settings: UserSettings)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.database.entities.UserSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE userId = :userId")
    fun getUserSettings(userId: String): Flow<UserSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(settings: UserSettingsEntity)

    @Update
    suspend fun updateUserSettings(settings: UserSettingsEntity)

    @Delete
    suspend fun deleteUserSettings(settings: UserSettingsEntity)

    @Query("UPDATE user_settings SET notificationsEnabled = :enabled WHERE userId = :userId")
    suspend fun updateNotificationsEnabled(userId: String, enabled: Boolean)

    @Query("UPDATE user_settings SET reminderTime = :time WHERE userId = :userId")
    suspend fun updateReminderTime(userId: String, time: String?)

    @Query("UPDATE user_settings SET theme = :theme WHERE userId = :userId")
    suspend fun updateTheme(userId: String, theme: String)
}
