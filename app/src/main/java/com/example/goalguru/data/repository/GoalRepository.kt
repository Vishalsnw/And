package com.example.goalguru.data.repository

import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun insertGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goalId: String)
    suspend fun getGoal(goalId: String): Goal?
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun updateGoalProgress(goalId: String, progress: Float)
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)

    // Daily tasks
    suspend fun insertDailyTask(task: DailyTask)
    suspend fun updateDailyTask(task: DailyTask)
    suspend fun deleteDailyTask(taskId: String)
    fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>>
    fun getTodaysTasks(): Flow<List<DailyTask>>
    suspend fun markTaskCompleted(taskId: String)
}
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun insertGoal(goal: Goal): Long
    suspend fun getGoal(id: String): Goal?
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun updateGoalProgress(goalId: String, progress: Float)
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)
    fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>>
    suspend fun insertDailyTask(task: DailyTask)
    suspend fun updateDailyTask(task: DailyTask)
}
