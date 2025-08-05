
package com.example.goalguru.di

import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.AIRepositoryImpl
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.GoalRepositoryImpl
import com.example.goalguru.data.repository.UserRepository
import com.example.goalguru.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGoalRepository(
        goalRepositoryImpl: GoalRepositoryImpl
    ): GoalRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAIRepository(
        aiRepositoryImpl: AIRepositoryImpl
    ): AIRepository
}
