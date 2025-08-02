package com.example.goalguru.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                goalRepository.getAllGoals().collect { goalList ->
                    _goals.value = goalList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun markGoalCompleted(goalId: String) {
        viewModelScope.launch {
            try {
                goalRepository.updateGoalProgress(goalId, 1.0f)
                goalRepository.updateGoalStatus(goalId, Goal.Status.COMPLETED)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshGoals() {
        loadGoals()
    }

    fun updateGoalProgress(goalId: String, progress: Float) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentGoals = _goals.value
                val updatedGoals = currentGoals.map { goal ->
                    if (goal.id == goalId) {
                        goal.copy(progress = progress)
                    } else {
                        goal
                    }
                }
                _goals.value = updatedGoals

                goalRepository.updateGoal(
                    updatedGoals.first { it.id == goalId }
                )
            } catch (e: Exception) {
                //_uiState.value = _uiState.value.copy(
                //    error = "Failed to update progress: ${e.message}"
                //)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                goalRepository.deleteGoal(goal)
                val updatedGoals = _goals.value.filter { it.id != goal.id }
                _goals.value = updatedGoals
            } catch (e: Exception) {
                //_uiState.value = _uiState.value.copy(
                //    error = "Failed to delete goal: ${e.message}"
                //)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}