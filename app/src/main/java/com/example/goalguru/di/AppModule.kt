package com.example.goalguru.di

import android.content.Context
import androidx.room.Room
import com.example.goalguru.data.database.*
import com.example.goalguru.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GoalGuruDatabase {
        return Room.databaseBuilder(
            context,
            GoalGuruDatabase::class.java,
            GoalGuruDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideGoalDao(database: GoalGuruDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideTaskDao(database: GoalGuruDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideUserDao(database: GoalGuruDatabase): UserDao = database.userDao()

    @Provides
    fun provideUserSettingsDao(database: GoalGuruDatabase): UserSettingsDao = database.userSettingsDao()

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao, taskDao: TaskDao): GoalRepository {
        return GoalRepository(goalDao, taskDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideAIRepository(): AIRepository {
        return AIRepository()
    }
}
