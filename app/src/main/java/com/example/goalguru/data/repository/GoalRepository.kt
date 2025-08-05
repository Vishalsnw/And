package com.example.goalguru.data.repository

import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getAllGoals(): Flow<List<Goal>>
    fun getGoalById(goalId: String): Flow<Goal?>
    fun getTasksForGoal(goalId: String): Flow<List<DailyTask>>
    suspend fun insertGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun updateGoalProgress(goalId: String, progress: Float)
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)
    suspend fun markTaskCompleted(taskId: String, completedAt: Long?)
    
    suspend fun getTasksForDate(date: String): List<DailyTask>
}