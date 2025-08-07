
package com.example.goalguru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goalguru.data.database.entities.GoalEntity
import com.example.goalguru.data.database.entities.TaskEntity
import com.example.goalguru.data.database.entities.UserEntity
import com.example.goalguru.data.database.entities.UserSettingsEntity
import com.example.goalguru.data.util.Converters

@Database(
    entities = [
        com.example.goalguru.data.database.entities.GoalEntity::class,
        com.example.goalguru.data.database.entities.TaskEntity::class,
        com.example.goalguru.data.database.entities.UserEntity::class,
        com.example.goalguru.data.database.entities.UserSettingsEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GoalGuruDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
    abstract fun userSettingsDao(): UserSettingsDao
}
