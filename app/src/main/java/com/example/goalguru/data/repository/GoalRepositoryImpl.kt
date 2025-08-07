package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.mappers.toGoal
import com.example.goalguru.data.mappers.toGoalEntity
import com.example.goalguru.data.mappers.toDailyTask
import com.example.goalguru.data.mappers.toTaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) : GoalRepository {

    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals().map { goals ->
            goals.map { goalEntity -> goalEntity.toGoal() }
        }

    override fun getGoalById(goalId: String): Flow<Goal?> = goalDao.getGoalById(goalId).map { it?.toGoal() }

    override fun getTasksForGoal(goalId: String): Flow<List<DailyTask>> = taskDao.getTasksForGoal(goalId).map { tasks ->
        tasks.map { taskEntity -> taskEntity.toDailyTask() }
    }

    override suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal.toGoalEntity())

    override suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal.toGoalEntity())

    override suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal.toGoalEntity())

    override suspend fun updateGoalProgress(goalId: String, progress: Float) =
        goalDao.updateGoalProgress(goalId, progress)

    override suspend fun updateGoalStatus(goalId: String, status: Goal.Status) =
        goalDao.updateGoalStatus(goalId, status.name)

    override suspend fun markTaskCompleted(taskId: String, completedAt: Long?) =
        taskDao.updateTaskCompletion(taskId, true, completedAt)

    override suspend fun getTasksForDate(date: String): List<DailyTask> {
        return try {
            // For now, return all tasks - you can implement date filtering in TaskDao later
            getAllGoals().firstOrNull()?.flatMap { goal ->
                getTasksForGoal(goal.id).firstOrNull() ?: emptyList()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}