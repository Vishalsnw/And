
package com.example.goalguru.di

import android.content.Context
import androidx.room.Room
import com.example.goalguru.data.database.AppDatabase
import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "goal_guru_database"
        ).build()
    }

    @Provides
    fun provideGoalDao(database: AppDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}
