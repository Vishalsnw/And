
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

    override suspend fun getGoal(id: String): Goal? {
        return goalDao.getGoalById(id)
    }

    override suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal)
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    override suspend fun deleteGoal(goalId: String) {
        goalDao.deleteGoalById(goalId)
        taskDao.deleteTasksForGoal(goalId)
    }

    override suspend fun markGoalCompleted(goalId: String) {
        val goal = goalDao.getGoalById(goalId)
        if (goal != null) {
            val updatedGoal = goal.copy(
                isCompleted = true,
                completedAt = System.currentTimeMillis(),
                progress = 1.0f
            )
            goalDao.updateGoal(updatedGoal)
        }
    }

    override suspend fun updateGoalProgress(goalId: String, progress: Float) {
        val goal = goalDao.getGoalById(goalId)
        if (goal != null) {
            val updatedGoal = goal.copy(progress = progress)
            goalDao.updateGoal(updatedGoal)
        }
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

    override suspend fun markTaskCompleted(taskId: String) {
        taskDao.markTaskCompleted(taskId, System.currentTimeMillis())
    }
}
