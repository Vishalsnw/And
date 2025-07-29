package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) {
    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    suspend fun getGoalById(goalId: String): Goal? = goalDao.getGoalById(goalId)

    fun getTasksForGoal(goalId: String): Flow<List<Task>> = taskDao.getTasksForGoal(goalId)

    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)

    suspend fun deleteGoalById(goalId: String) = goalDao.deleteGoalById(goalId)
}