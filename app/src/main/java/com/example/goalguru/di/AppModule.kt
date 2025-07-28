
package com.example.goalguru.di

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

    @Provides
    @Singleton
    fun provideGoalRepository(): GoalRepository {
        return GoalRepository()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }

    @Provides
    @Singleton
    fun provideAIRepository(): AIRepository {
        return AIRepository()
    }
}
