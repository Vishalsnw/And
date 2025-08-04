
package com.example.goalguru.data.database

import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: String): Goal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM goals WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: String)

    @Query("SELECT * FROM goals WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getActiveGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedGoals(): Flow<List<Goal>>
}
package com.example.goalguru.data.database

import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: String): Goal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM goals WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: String)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoal(id: String): Goal?

    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("UPDATE goals SET progress = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: String, progress: Float)

    @Query("UPDATE goals SET status = :status WHERE id = :goalId")
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)
}
