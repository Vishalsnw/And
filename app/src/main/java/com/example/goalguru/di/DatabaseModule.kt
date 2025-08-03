package com.example.goalguru.di

import android.content.Context
import androidx.room.Room
import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.GoalGuruDatabase
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.AIRepositoryImpl
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.GoalRepositoryImpl
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
    fun provideGoalGuruDatabase(@ApplicationContext context: Context): GoalGuruDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GoalGuruDatabase::class.java,
            "goal_guru_database"
        ).build()
    }

    @Provides
    fun provideGoalDao(database: GoalGuruDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideTaskDao(database: GoalGuruDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideUserDao(database: GoalGuruDatabase): UserDao = database.userDao()

    @Provides
    fun provideUserSettingsDao(database: GoalGuruDatabase): UserSettingsDao {
        return database.userSettingsDao()
    }

    @Provides
    @Singleton
    fun provideGoalRepository(
        goalDao: GoalDao,
        taskDao: TaskDao
    ): GoalRepository {
        return GoalRepositoryImpl(goalDao, taskDao)
    }

    @Provides
    @Singleton
    fun provideAIRepository(impl: AIRepositoryImpl): AIRepository = impl
}