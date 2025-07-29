
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.Goal
import com.example.goalguru.firebase.FirebaseConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor() {

    private val firestore = FirebaseConfig.firestore
    private val goalsCollection = firestore.collection("goals")

    fun getAllGoals(): Flow<List<Goal>> = callbackFlow {
        val listener = goalsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val goals = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Goal::class.java)?.copy(id = doc.id)
            } ?: emptyList()

            trySend(goals)
        }

        awaitClose { listener.remove() }
    }

    suspend fun insertGoal(goal: Goal) {
        goalsCollection.document(goal.id).set(goal).await()
    }

    suspend fun updateGoal(goal: Goal) {
        goalsCollection.document(goal.id).set(goal).await()
    }

    suspend fun deleteGoal(goalId: String) {
        goalsCollection.document(goalId).delete().await()
    }

    suspend fun getGoalById(goalId: String): Goal? {
        return try {
            val doc = goalsCollection.document(goalId).get().await()
            doc.toObject(Goal::class.java)?.copy(id = doc.id)
        } catch (e: Exception) {
            null
        }
    }
}
package com.example.goalguru.data.repository

import com.example.goalguru.data.database.GoalDao
import com.example.goalguru.data.database.TaskDao
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) {
    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    suspend fun getGoalById(goalId: String): Goal? = goalDao.getGoalById(goalId)

    fun getTasksForGoal(goalId: String): Flow<List<Task>> = taskDao.getTasksForGoal(goalId)

    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)

    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)

    suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)

    suspend fun deleteGoalById(goalId: String) = goalDao.deleteGoalById(goalId)
}
