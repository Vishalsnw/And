package com.example.goalguru.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.util.Converters
import com.example.goalguru.data.dao.GoalDao
import com.example.goalguru.data.dao.TaskDao

@Database(
    entities = [Goal::class, DailyTask::class, User::class, UserSettings::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class GoalGuruDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
    abstract fun userSettingsDao(): UserSettingsDao
}