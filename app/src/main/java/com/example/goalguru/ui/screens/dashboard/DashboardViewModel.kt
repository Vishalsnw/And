package com.example.goalguru.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals = _goals.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                goalRepository.getAllGoals().collectLatest { goalsList ->
                    _goals.value = goalsList
                    _uiState.value = _uiState.value.copy(
                        goals = goalsList,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load goals: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshGoals() {
        loadGoals()
    }

    fun markGoalAsCompleted(goalId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentGoals = _goals.value
                val updatedGoals = currentGoals.map { goal ->
                    if (goal.id == goalId) {
                        goal.copy(
                            status = Goal.Status.COMPLETED,
                            progress = 1.0f,
                            completedAt = java.time.LocalDateTime.now()
                        )
                    } else {
                        goal
                    }
                }
                _goals.value = updatedGoals

                goalRepository.updateGoal(
                    updatedGoals.first { it.id == goalId }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
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
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update progress: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteGoal(goalId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                goalRepository.deleteGoal(goalId)
                val updatedGoals = _goals.value.filter { it.id != goalId }
                _goals.value = updatedGoals
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}