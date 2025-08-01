package com.example.goalguru.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import android.content.Context
import com.example.goalguru.data.util.Converters

@Database(
    entities = [Goal::class, Task::class, User::class, UserSettings::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class GoalGuruDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: GoalGuruDatabase? = null

        fun getDatabase(context: Context): GoalGuruDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalGuruDatabase::class.java,
                    "goal_guru_database",
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}