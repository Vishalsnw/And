
package com.example.goalguru.data.dao

import androidx.room.*
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY dueDate ASC")
    fun getDailyTasksForGoal(goalId: String): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE('now') ORDER BY dueDate ASC")
    fun getTodaysTasks(): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks ORDER BY dueDate ASC")
    fun getAllDailyTasks(): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks WHERE id = :taskId")
    suspend fun getDailyTaskById(taskId: String): DailyTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyTask(task: DailyTask)

    @Update
    suspend fun updateDailyTask(task: DailyTask)

    @Query("DELETE FROM daily_tasks WHERE id = :taskId")
    suspend fun deleteDailyTask(taskId: String)

    @Query("UPDATE daily_tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    suspend fun markTaskCompleted(taskId: String, completedAt: Date)
}
