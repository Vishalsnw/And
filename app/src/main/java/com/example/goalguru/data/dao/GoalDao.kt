
package com.example.goalguru.data.dao

import androidx.room.*
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoal(goalId: String): Goal?

    @Query("SELECT * FROM goals WHERE userId = :userId ORDER BY createdAt DESC")
    fun getGoalsByUserId(userId: String): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Query("DELETE FROM goals WHERE id = :goalId")
    suspend fun deleteGoal(goalId: String)

    @Query("UPDATE goals SET progress = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: String, progress: Float)

    @Query("UPDATE goals SET status = :status WHERE id = :goalId")
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)

    @Query("SELECT COUNT(*) FROM goals WHERE userId = :userId")
    suspend fun getGoalCountByUserId(userId: String): Int

    // Daily Tasks
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyTask(task: DailyTask)

    @Update
    suspend fun updateDailyTask(task: DailyTask)

    @Query("DELETE FROM daily_tasks WHERE id = :taskId")
    suspend fun deleteDailyTask(taskId: String)

    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY dueDate ASC")
    fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE('now') ORDER BY dueDate ASC")
    fun getTodaysTasks(): Flow<List<DailyTask>>

    @Query("UPDATE daily_tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    suspend fun markTaskCompleted(taskId: String, completedAt: Date)
}
