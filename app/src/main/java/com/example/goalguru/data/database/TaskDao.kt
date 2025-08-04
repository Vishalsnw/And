
package com.example.goalguru.data.database

import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY createdAt DESC")
    fun getTasksForGoal(goalId: String): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): DailyTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: DailyTask)

    @Update
    suspend fun updateTask(task: DailyTask)

    @Delete
    suspend fun deleteTask(task: DailyTask)

    @Query("DELETE FROM daily_tasks WHERE goalId = :goalId")
    suspend fun deleteTasksForGoal(goalId: String)

    @Query("SELECT * FROM daily_tasks WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getActiveTasks(): Flow<List<DailyTask>>

    @Query("UPDATE daily_tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    suspend fun markTaskCompleted(taskId: String, completedAt: Long)
}
package com.example.goalguru.data.database

import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY dueDate ASC")
    fun getTasksForGoal(goalId: String): Flow<List<DailyTask>>

    @Query("SELECT * FROM daily_tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): DailyTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: DailyTask)

    @Update
    suspend fun updateTask(task: DailyTask)

    @Delete
    suspend fun deleteTask(task: DailyTask)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY date ASC")
    fun getTasksForGoal(goalId: String): Flow<List<DailyTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: DailyTask)

    @Update
    suspend fun updateTask(task: DailyTask)

    @Delete
    suspend fun deleteTask(task: DailyTask)

    @Query("UPDATE daily_tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean)
}
