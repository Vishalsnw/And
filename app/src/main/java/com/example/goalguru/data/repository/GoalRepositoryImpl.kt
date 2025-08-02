
package com.example.goalguru.data.repository

import com.example.goalguru.data.dao.GoalDao
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {

    override suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal)
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    override suspend fun deleteGoal(goalId: String) {
        goalDao.deleteGoal(goalId)
    }

    override suspend fun getGoal(goalId: String): Goal? {
        return goalDao.getGoal(goalId)
    }

    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals()
    }

    override suspend fun updateGoalProgress(goalId: String, progress: Float) {
        goalDao.updateGoalProgress(goalId, progress)
    }

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) {
        goalDao.updateGoalStatus(goalId, status)
    }

    override suspend fun insertDailyTask(task: DailyTask) {
        goalDao.insertDailyTask(task)
    }

    override suspend fun updateDailyTask(task: DailyTask) {
        goalDao.updateDailyTask(task)
    }

    override suspend fun deleteDailyTask(taskId: String) {
        goalDao.deleteDailyTask(taskId)
    }

    override fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>> {
        return goalDao.getDailyTasksForGoal(goalId)
    }

    override fun getTodaysTasks(): Flow<List<DailyTask>> {
        return goalDao.getTodaysTasks()
    }

    override suspend fun markTaskCompleted(taskId: String) {
        goalDao.markTaskCompleted(taskId, Date())
    }
}
