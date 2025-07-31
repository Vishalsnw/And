package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Task
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {

    private val _goal = MutableStateFlow<Goal?>(null)
    val goal: StateFlow<Goal?> = _goal.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadGoal(goalId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                goalRepository.getGoalById(goalId)?.let {
                    _goal.value = it
                }
                goalRepository.getTasksForGoal(goalId).collect {
                    _tasks.value = it
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load goal"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleGoalCompletion() {
        val currentGoal = _goal.value ?: return
        viewModelScope.launch {
            try {
                val updatedGoal = currentGoal.copy(
                    isCompleted = !currentGoal.isCompleted,
                    updatedAt = Date()
                )
                goalRepository.updateGoal(updatedGoal)
                _goal.value = updatedGoal
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update goal"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
