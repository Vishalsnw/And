package com.example.goalguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    fun getGoalById(goalId: String): Flow<Goal?>

    @Query("SELECT * FROM goals WHERE status = :status")
    fun getGoalsByStatus(status: Goal.Status): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("UPDATE goals SET currentProgress = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: String, progress: Float)

    @Query("UPDATE goals SET status = :status WHERE id = :goalId")
    suspend fun updateGoalStatus(goalId: String, status: Goal.Status)
}
package com.example.goalguru.data.database

import androidx.room.*
import com.example.goalguru.data.database.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    fun getGoalById(goalId: String): Flow<GoalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)

    @Query("UPDATE goals SET progress = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: String, progress: Float)

    @Query("UPDATE goals SET status = :status WHERE id = :goalId")
    suspend fun updateGoalStatus(goalId: String, status: String)
}
