
package com.example.goalguru.data.dao

import androidx.room.*
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

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

    
}
