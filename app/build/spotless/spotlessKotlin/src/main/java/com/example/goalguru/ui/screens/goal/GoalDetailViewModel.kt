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
import kotlinx.coroutines.launch
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

    fun loadGoal(goalId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                goalRepository.getGoalById(goalId)?.let { goal ->
                    _goal.value = goal
                }
                goalRepository.getTasksForGoal(goalId).collect { tasks ->
                    _tasks.value = tasks
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
