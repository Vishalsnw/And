package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

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

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao,
) : GoalRepository {

    override suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    override suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    override suspend fun deleteGoal(goalId: String) = goalDao.deleteGoalById(goalId)

    override suspend fun getGoal(goalId: String): Goal? = goalDao.getGoalById(goalId)

    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    override suspend fun updateGoalProgress(goalId: String, progress: Float) {
        val goal = goalDao.getGoalById(goalId)
        goal?.let {
            goalDao.updateGoal(it.copy(progress = progress))
        }
    }

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) {
        val goal = goalDao.getGoalById(goalId)
        goal?.let {
            goalDao.updateGoal(it.copy(status = status))
        }
    }

    override suspend fun insertDailyTask(task: DailyTask) = taskDao.insertDailyTask(task)

    override suspend fun updateDailyTask(task: DailyTask) = taskDao.updateDailyTask(task)

    override suspend fun deleteDailyTask(taskId: String) = taskDao.deleteDailyTask(taskId)

    override fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>> = taskDao.getDailyTasksForGoal(goalId)

    override fun getTodaysTasks(): Flow<List<DailyTask>> {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)

        return taskDao.getAllDailyTasks().map { tasks ->
            tasks.filter { task -> task.date == formattedDate }
        }
    }

    override suspend fun markTaskCompleted(taskId: String) {
        val task = taskDao.getDailyTaskById(taskId)
        task?.let {
            taskDao.updateDailyTask(it.copy(isCompleted = true))
        }
    }
}