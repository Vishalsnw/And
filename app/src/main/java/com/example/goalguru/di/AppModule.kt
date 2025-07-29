
package com.example.goalguru.di

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Using Firebase Firestore as primary database
    // Room database removed to avoid conflicts

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
