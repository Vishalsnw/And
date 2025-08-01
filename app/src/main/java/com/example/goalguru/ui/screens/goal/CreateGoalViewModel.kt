package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.AIRepository
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
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _category = MutableStateFlow("Personal")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _priority = MutableStateFlow(Goal.Priority.MEDIUM)
    val priority: StateFlow<Goal.Priority> = _priority.asStateFlow()

    private val _targetDate = MutableStateFlow<String?>(null)
    val targetDate: StateFlow<String?> = _targetDate.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isGoalCreated = MutableStateFlow(false)
    val isGoalCreated: StateFlow<Boolean> = _isGoalCreated.asStateFlow()

    private val _aiRoadmap = MutableStateFlow<String?>(null)
    val aiRoadmap: StateFlow<String?> = _aiRoadmap.asStateFlow()

    private val _isGeneratingRoadmap = MutableStateFlow(false)
    val isGeneratingRoadmap: StateFlow<Boolean> = _isGeneratingRoadmap.asStateFlow()

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
        clearError()
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
        clearError()
    }

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
    }

    fun updatePriority(newPriority: Goal.Priority) {
        _priority.value = newPriority
    }

    fun updateTargetDate(newTargetDate: String?) {
        _targetDate.value = newTargetDate
    }

    fun generateAIRoadmap() {
        if (_title.value.isBlank()) {
            _error.value = "Please enter a goal title first"
            return
        }

        viewModelScope.launch {
            _isGeneratingRoadmap.value = true
            try {
                val roadmap = aiRepository.generateGoalRoadmap(
                    goalTitle = _title.value,
                    goalDescription = _description.value.ifBlank { "No description provided" }
                )
                _aiRoadmap.value = roadmap
            } catch (e: Exception) {
                _error.value = "Failed to generate AI roadmap: ${e.message}"
            } finally {
                _isGeneratingRoadmap.value = false
            }
        }
    }

    fun useAIRoadmap() {
        _aiRoadmap.value?.let { roadmap ->
            val currentDescription = _description.value
            val newDescription = if (currentDescription.isBlank()) {
                roadmap
            } else {
                "$currentDescription\n\n--- AI Generated Roadmap ---\n$roadmap"
            }
            _description.value = newDescription
            _aiRoadmap.value = null
        }
    }

    fun clearAIRoadmap() {
        _aiRoadmap.value = null
    }

    fun createGoal() {
        if (_title.value.isBlank()) {
            _error.value = "Title cannot be empty"
            return
        }

        if (_description.value.isBlank()) {
            _error.value = "Description cannot be empty"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentTime = java.time.LocalDateTime.now()
                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    userId = "default_user", // You may want to get this from user session
                    title = _title.value.trim(),
                    description = _description.value.trim(),
                    category = _category.value,
                    priority = _priority.value,
                    status = Goal.Status.NOT_STARTED,
                    targetDate = null,
                    createdAt = currentTime,
                    updatedAt = currentTime,
                    completedAt = null,
                    progress = 0f
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