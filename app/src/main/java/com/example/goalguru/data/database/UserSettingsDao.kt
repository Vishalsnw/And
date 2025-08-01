package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {

    @Query("SELECT * FROM user_settings WHERE userId = :userId LIMIT 1")
    fun getUserSettings(userId: String = "default_user"): Flow<UserSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(settings: UserSettings)

    @Update
    suspend fun updateUserSettings(settings: UserSettings)
}
