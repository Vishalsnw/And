
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_settings LIMIT 1")
    fun getUserSettings(): Flow<UserSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(userSettings: UserSettings)

    @Update
    suspend fun updateUserSettings(userSettings: UserSettings)
}
