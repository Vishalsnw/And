
package com.example.goalguru.di

import android.content.Context
import androidx.room.Room
import com.example.goalguru.data.database.GoalGuruDatabase
import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.database.UserDao
import com.example.goalguru.data.database.UserSettingsDao
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.GoalRepositoryImpl
import com.example.goalguru.data.repository.UserRepository
import com.example.goalguru.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    abstract fun bindGoalRepository(
        goalRepositoryImpl: GoalRepositoryImpl
    ): GoalRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    companion object {
        @Provides
        @Singleton
        fun provideGoalGuruDatabase(
            @ApplicationContext context: Context
        ): GoalGuruDatabase {
            return Room.databaseBuilder(
                context,
                GoalGuruDatabase::class.java,
                "goal_guru_database"
            ).fallbackToDestructiveMigration()
             .build()
        }

        @Provides
        fun provideGoalDao(database: GoalGuruDatabase): GoalDao = database.goalDao()

        @Provides
        fun provideTaskDao(database: GoalGuruDatabase): TaskDao = database.taskDao()

        @Provides
        fun provideUserDao(database: GoalGuruDatabase): UserDao = database.userDao()

        @Provides
        fun provideUserSettingsDao(database: GoalGuruDatabase): UserSettingsDao = database.userSettingsDao()
    }
}
