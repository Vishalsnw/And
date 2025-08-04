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

    override suspend fun insertGoal(goal: Goal): Long {
        return goalDao.insertGoal(goal)
    }

    override suspend fun getGoal(id: String): Goal? {
        return goalDao.getGoal(id)
    }

    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals()
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    override suspend fun deleteGoal(goal: Goal) {
        goalDao.deleteGoal(goal)
    }

    override suspend fun updateGoalProgress(goalId: String, progress: Float) {
        goalDao.updateGoalProgress(goalId, progress)
    }

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) {
        goalDao.updateGoalStatus(goalId, status)
    }

    override fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>> {
        return taskDao.getTasksForGoal(goalId)
    }

    override suspend fun insertDailyTask(task: DailyTask) {
        taskDao.insertTask(task)
    }

    override suspend fun updateDailyTask(task: DailyTask) {
        taskDao.updateTask(task)
    }
}