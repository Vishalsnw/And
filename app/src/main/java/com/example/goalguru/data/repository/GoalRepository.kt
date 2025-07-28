package com.example.goalguru.data.repository

import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor() {

    private val _goals = mutableListOf<Goal>()

    fun getAllGoals(): Flow<List<Goal>> {
        return flowOf(_goals.toList())
    }

    suspend fun insertGoal(goal: Goal) {
        _goals.add(goal)
    }

    suspend fun updateGoal(goal: Goal) {
        val index = _goals.indexOfFirst { it.id == goal.id }
        if (index != -1) {
            _goals[index] = goal
        }
    }

    suspend fun deleteGoal(goalId: String) {
        _goals.removeAll { it.id == goalId }
    }

    suspend fun getGoalById(goalId: String): Goal? {
        return _goals.find { it.id == goalId }
    }
}