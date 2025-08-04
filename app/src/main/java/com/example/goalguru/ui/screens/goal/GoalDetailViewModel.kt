package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _goal = MutableStateFlow<Goal?>(null)
    val goal: StateFlow<Goal?> = _goal.asStateFlow()

    private val _dailyTasks = MutableStateFlow<List<DailyTask>>(emptyList())
    val dailyTasks: StateFlow<List<DailyTask>> = _dailyTasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadGoal(goalId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                goalRepository.getGoalById(goalId).collect { goal ->
                    _goal.value = goal
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDailyTasks(goalId: String) {
        viewModelScope.launch {
            goalRepository.getTasksForGoal(goalId).collect { tasks ->
                _dailyTasks.value = tasks
            }
        }
    }

    fun markTaskCompleted(taskId: String) {
        viewModelScope.launch {
            goalRepository.markTaskCompleted(taskId, System.currentTimeMillis())
        }
    }
}