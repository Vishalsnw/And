
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
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _goal = MutableStateFlow<Goal?>(null)
    val goal: StateFlow<Goal?> = _goal.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun loadGoal(goalId: String) {
        viewModelScope.launch {
            try {
                _goal.value = goalRepository.getGoalById(goalId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadTasks(goalId: String) {
        viewModelScope.launch {
            try {
                // For now, return empty list since we don't have task repository yet
                _tasks.value = emptyList()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun toggleTaskCompletion(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                // TODO: Implement task completion toggle when task repository is available
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
