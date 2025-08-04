package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) : GoalRepository {

    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    override fun getGoalById(goalId: String): Flow<Goal?> = goalDao.getGoalById(goalId)

    override fun getTasksForGoal(goalId: String): Flow<List<DailyTask>> = taskDao.getTasksForGoal(goalId)

    override suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    override suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    override suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)

    override suspend fun updateGoalProgress(goalId: String, progress: Float) = 
        goalDao.updateGoalProgress(goalId, progress)

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) = 
        goalDao.updateGoalStatus(goalId, status)

    override suspend fun markTaskCompleted(taskId: String, completedAt: Long?) = 
        taskDao.updateTaskCompletion(taskId, true, completedAt)
}