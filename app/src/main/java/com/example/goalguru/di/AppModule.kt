
package com.example.goalguru.di

import android.content.Context
import com.example.goalguru.data.database.AppDatabase
import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideGoalDao(database: AppDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao): GoalRepository {
        return GoalRepository(goalDao)
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
