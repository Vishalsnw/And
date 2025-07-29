
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) {
    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    suspend fun deleteGoal(goalId: String) = goalDao.deleteGoal(goalId)

    suspend fun getGoalById(goalId: String): Goal? = goalDao.getGoalById(goalId)

    suspend fun getTasksForGoal(goalId: String): Flow<List<Task>> = taskDao.getTasksForGoal(goalId)

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(taskId: String) = taskDao.deleteTask(taskId)
}
