package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao,
) {

    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    suspend fun getGoalById(goalId: String): Goal? = goalDao.getGoalById(goalId)

    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)

    suspend fun deleteGoalById(goalId: String) = goalDao.deleteGoalById(goalId)

    fun getTasksForGoal(goalId: String): Flow<List<Task>> = taskDao.getTasksForGoal(goalId)
    
    // Add this helper method
    suspend fun getTasksForGoalSync(goalId: String): List<Task> = 
        taskDao.getTasksForGoal(goalId).firstOrNull() ?: emptyList()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun updateGoalProgress(goalId: String) {
        val tasks = getTasksForGoalSync(goalId) // Use the new helper method
        val completedTasks = tasks.count { it.isCompleted }
        val progress = if (tasks.isNotEmpty()) {
            completedTasks.toFloat() / tasks.size
        } else {
            0f
        }

        val goal = goalDao.getGoalById(goalId)
        goal?.let {
            goalDao.updateGoal(it.copy(progress = progress))
        }
    }
}
