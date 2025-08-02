package com.example.goalguru.di

import android.content.Context
import androidx.room.Room
import com.example.goalguru.data.database.GoalGuruDatabase
import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.repository.AIRepository
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
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideGoalDao(database: GoalGuruDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideTaskDao(database: GoalGuruDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideUserSettingsDao(database: GoalGuruDatabase): UserSettingsDao {
        return database.userSettingsDao()
    }

    @Provides
    fun provideUserDao(database: GoalGuruDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideAIRepository(impl: AIRepositoryImpl): AIRepository = impl

    @Provides
    @Singleton
    fun provideAIRepository(): AIRepository = AIRepository()
}