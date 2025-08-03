
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

    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals()
    }

    override suspend fun getGoalById(goalId: String): Goal? {
        return goalDao.getGoalById(goalId)
    }

    override suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal)
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    override suspend fun deleteGoal(goal: Goal) {
        goalDao.deleteGoal(goal)
    }

    override suspend fun deleteGoalById(goalId: String) {
        goalDao.deleteGoalById(goalId)
    }

    override fun getActiveGoals(): Flow<List<Goal>> {
        return goalDao.getActiveGoals()
    }

    override fun getCompletedGoals(): Flow<List<Goal>> {
        return goalDao.getCompletedGoals()
    }

    override fun getTasksForGoal(goalId: String): Flow<List<DailyTask>> {
        return taskDao.getTasksForGoal(goalId)
    }

    override suspend fun getTaskById(taskId: String): DailyTask? {
        return taskDao.getTaskById(taskId)
    }

    override suspend fun insertDailyTask(task: DailyTask) {
        taskDao.insertTask(task)
    }

    override suspend fun updateDailyTask(task: DailyTask) {
        taskDao.updateTask(task)
    }

    override suspend fun markTaskCompleted(taskId: String) {
        taskDao.markTaskCompleted(taskId, System.currentTimeMillis())
    }
}
