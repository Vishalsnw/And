
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: Flow<List<Goal>> = _goals.asStateFlow()

    suspend fun insertGoal(goal: Goal) {
        val currentGoals = _goals.value.toMutableList()
        currentGoals.add(goal)
        _goals.value = currentGoals
    }

    suspend fun updateGoal(goal: Goal) {
        val currentGoals = _goals.value.toMutableList()
        val index = currentGoals.indexOfFirst { it.id == goal.id }
        if (index != -1) {
            currentGoals[index] = goal
            _goals.value = currentGoals
        }
    }

    suspend fun deleteGoal(goalId: String) {
        val currentGoals = _goals.value.toMutableList()
        currentGoals.removeAll { it.id == goalId }
        _goals.value = currentGoals
    }

    suspend fun getGoalById(goalId: String): Goal? {
        return _goals.value.find { it.id == goalId }
    }
}
