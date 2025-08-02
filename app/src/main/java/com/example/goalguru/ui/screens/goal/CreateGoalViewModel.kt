package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.AIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.LocalDateTime
import java.util.UUID

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uiState = MutableStateFlow(CreateGoalUiState())
    val uiState = _uiState.asStateFlow()

    data class CreateGoalUiState(
        val title: String = "",
        val description: String = "",
        val error: String? = null,
        val isGoalCreated: Boolean = false,
        val generatedGoal: Goal? = null
    )

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun clearGenerated() {
        _uiState.value = _uiState.value.copy(
            generatedGoal = null,
            error = null
        )
    }

    fun generateGoal() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(error = null)
                _isLoading.value = true

                val prompt = "Create a detailed goal plan for: ${_uiState.value.title}. Description: ${_uiState.value.description}"
                val aiResponse = aiRepository.generateGoalSuggestions(prompt)

                if (aiResponse != null) {
                    val goal = Goal(
                        id = UUID.randomUUID().toString(),
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        category = "General",
                        targetDate = LocalDateTime.now().plusDays(30),
                        status = Goal.Status.NOT_STARTED,
                        priority = Goal.Priority.MEDIUM,
                        progress = 0.0f,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        userId = "default_user",
                        completedAt = null
                    )
                    _uiState.value = _uiState.value.copy(generatedGoal = goal)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to generate goal suggestions")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to generate goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Adding the missing method
    fun generateGoalRoadmap(goalInput: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(error = null)
                _isLoading.value = true

                val prompt = "Create a roadmap for: $goalInput"
                val aiResponse = aiRepository.generateGoalSuggestions(prompt)

                if (aiResponse != null) {
                    val goal = Goal(
                        id = UUID.randomUUID().toString(),
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        category = "General",
                        targetDate = LocalDateTime.now().plusDays(30),
                        status = Goal.Status.NOT_STARTED,
                        priority = Goal.Priority.MEDIUM,
                        progress = 0.0f,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        userId = "default_user",
                        completedAt = null
                    )
                    _uiState.value = _uiState.value.copy(generatedGoal = goal)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to generate goal roadmap")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to generate goal roadmap: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createGoal() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(error = null)
                _isLoading.value = true

                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    category = "General",
                    targetDate = LocalDateTime.now().plusDays(30),
                    status = Goal.Status.NOT_STARTED,
                    priority = Goal.Priority.MEDIUM,
                    progress = 0.0f,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    userId = "default_user",
                    completedAt = null
                )

                goalRepository.insertGoal(goal)
                _uiState.value = _uiState.value.copy(isGoalCreated = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to create goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveGoal() {
        val generatedGoal = _uiState.value.generatedGoal
        if (generatedGoal != null) {
            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    goalRepository.insertGoal(generatedGoal)
                    _uiState.value = _uiState.value.copy(isGoalCreated = true)
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to save goal: ${e.message}"
                    )
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun updateGoal(goalId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(error = null)
                _isLoading.value = true

                val updatedGoal = Goal(
                    id = goalId,
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    category = "General",
                    targetDate = LocalDateTime.now().plusDays(30),
                    status = Goal.Status.NOT_STARTED,
                    priority = Goal.Priority.MEDIUM,
                    progress = 0.0f,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    userId = "default_user",
                    completedAt = null
                )

                goalRepository.updateGoal(updatedGoal)
                _uiState.value = _uiState.value.copy(isGoalCreated = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}