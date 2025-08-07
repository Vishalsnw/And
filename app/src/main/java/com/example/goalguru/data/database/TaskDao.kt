
package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY date ASC")
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

    @Query("SELECT * FROM daily_tasks WHERE isCompleted = 0 ORDER BY date ASC")
    fun getActiveTasks(): Flow<List<DailyTask>>

    @Query("UPDATE daily_tasks SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean, completedAt: Long?)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks WHERE goalId = :goalId ORDER BY date ASC")
    fun getTasksForGoal(goalId: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM daily_tasks WHERE date(date) = :date")
    suspend fun getTasksForDate(date: String): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE daily_tasks SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean, completedAt: Long?)
}
