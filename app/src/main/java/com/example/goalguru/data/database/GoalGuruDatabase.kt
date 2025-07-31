/*
 * Copyright 2023 Your Name
 * Licensed under Apache-2.0
 */

package com.example.goalguru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goalguru.data.database.dao.GoalDao
import com.example.goalguru.data.database.dao.TaskDao
import com.example.goalguru.data.database.dao.UserDao
import com.example.goalguru.data.database.dao.UserSettingsDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import com.example.goalguru.data.model.User
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.util.Converters

@Database(
    entities = [
        Goal::class,
        User::class,
        Task::class,
        UserSettings::class, // ✅ Ensures table is created
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class GoalGuruDatabase : RoomDatabase() {

    abstract fun goalDao(): GoalDao
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun userSettingsDao(): UserSettingsDao // ✅ Exposed DAO method

    companion object {
        const val DATABASE_NAME = "goal_guru_db"
    }
}
