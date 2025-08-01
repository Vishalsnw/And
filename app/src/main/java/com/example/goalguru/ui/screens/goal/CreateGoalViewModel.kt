package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.AIRepository
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

    fun generateRoadmap() {
        viewModelScope.launch {
            try {
                _isGeneratingRoadmap.value = true
                _error.value = null

                val roadmap = aiRepository.generateGoalRoadmap(
                    goalTitle = _title.value,
                    goalDescription = _description.value
                )

                _aiRoadmap.value = roadmap ?: "Unable to generate roadmap. Please try again."
            } catch (e: Exception) {
                _error.value = "Failed to generate roadmap: ${e.message}"
            } finally {
                _isGeneratingRoadmap.value = false
            }
        }
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

                val currentTime = java.time.LocalDateTime.now()
                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    userId = "default_user", // You may want to get this from user session
                    title = _title.value.trim(),
                    description = _description.value.trim(),
                    category = "General",
                    priority = Goal.Priority.MEDIUM,
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