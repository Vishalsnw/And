package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isGoalCreated = MutableStateFlow(false)
    val isGoalCreated: StateFlow<Boolean> = _isGoalCreated.asStateFlow()

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun createGoal() {
        if (_title.value.isBlank()) {
            _error.value = "Title cannot be empty"
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    title = _title.value.trim(),
                    description = _description.value.trim(),
                    category = "General",
                    priority = Goal.Priority.MEDIUM,
                    isCompleted = false,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )

                goalRepository.insertGoal(goal)
                _isGoalCreated.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create goal"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun resetGoalCreated() {
        _isGoalCreated.value = false
    }
}