package com.example.goalguru.data.repository

import com.example.goalguru.data.dao.GoalDao
import com.example.goalguru.data.dao.TaskDao
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) : GoalRepository {

    override suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    override suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    override suspend fun deleteGoal(goalId: String) = goalDao.deleteGoal(goalId)

    override suspend fun getGoal(goalId: String): Goal? = goalDao.getGoal(goalId)

    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    override suspend fun updateGoalProgress(goalId: String, progress: Float) =
        goalDao.updateGoalProgress(goalId, progress)

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) =
        goalDao.updateGoalStatus(goalId, status)

    // Daily tasks using TaskDao
    override suspend fun insertDailyTask(task: DailyTask) = taskDao.insertDailyTask(task)

    override suspend fun updateDailyTask(task: DailyTask) = taskDao.updateDailyTask(task)

    override suspend fun deleteDailyTask(taskId: String) = taskDao.deleteDailyTask(taskId)

    override fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>> = 
        taskDao.getDailyTasksForGoal(goalId)

    override fun getTodaysTasks(): Flow<List<DailyTask>> = taskDao.getTodaysTasks()

    override suspend fun markTaskCompleted(taskId: String) {
        taskDao.markTaskCompleted(taskId, Date())
    }
}