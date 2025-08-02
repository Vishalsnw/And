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
import java.util.Date
import java.time.ZoneId

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
		val roadmap: String = "",
        val error: String? = null,
        val isGoalCreated: Boolean = false,
        val generatedGoal: Goal? = null,
        val category: String = "General",
        val targetDate: LocalDateTime = LocalDateTime.now().plusDays(30),
        val priority: Goal.Priority = Goal.Priority.MEDIUM,
        val estimatedDuration: Int = 30
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
                        roadmap = "",
                        targetDate = Date.from(_uiState.value.targetDate.atZone(ZoneId.systemDefault()).toInstant()),
                        priority = _uiState.value.priority,
                        estimatedDuration = _uiState.value.estimatedDuration,
                        createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                        progress = 0.0f
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
		fun createGoalFromAI(title: String, description: String, roadmap: String, category: String, targetDate: LocalDateTime, priority: Goal.Priority, estimatedDuration: Int) {
			viewModelScope.launch {
				try {
					_isLoading.value = true
					_uiState.value = _uiState.value.copy(error = null)

					val goal = Goal(
						title = title,
						description = description,
						roadmap = roadmap,
						targetDate = Date.from(targetDate.atZone(ZoneId.systemDefault()).toInstant()),
						priority = priority,
						estimatedDuration = estimatedDuration,
						createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
						progress = 0.0f
					)

					goalRepository.insertGoal(goal)
					_uiState.value = _uiState.value.copy(isGoalCreated = true)

				} catch (e: Exception) {
					_uiState.value = _uiState.value.copy(error = "Failed to create goal: ${e.message}")
				} finally {
					_isLoading.value = false
				}
			}
		}
		fun createGoalWithRoadmap(title: String, description: String, roadmap: String, category: String, targetDate: LocalDateTime, priority: Goal.Priority, estimatedDuration: Int) {
			viewModelScope.launch {
				try {
					_isLoading.value = true
					_uiState.value = _uiState.value.copy(error = null)

					val goal = Goal(
						title = title,
						description = description,
						roadmap = roadmap,
						targetDate = Date.from(targetDate.atZone(ZoneId.systemDefault()).toInstant()),
						priority = priority,
						estimatedDuration = estimatedDuration,
						createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
						progress = 0.0f
					)

					goalRepository.insertGoal(goal)
					_uiState.value = _uiState.value.copy(isGoalCreated = true)

				} catch (e: Exception) {
					_uiState.value = _uiState.value.copy(error = "Failed to create goal: ${e.message}")
				} finally {
					_isLoading.value = false
				}
			}
		}

    fun generateGoalRoadmap(goalInput: String) {
        if (goalInput.trim().isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Please enter a goal description")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    error = null,
                    roadmap = ""
                )
                _isLoading.value = true

                val prompt = "Create a detailed roadmap for: $goalInput"
                val aiResponse = aiRepository.generateGoalSuggestions(prompt)

                if (aiResponse != null) {
                    _uiState.value = _uiState.value.copy(
                        roadmap = aiResponse,
                        title = goalInput.take(50), // Auto-fill title
                        description = goalInput
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Unable to generate roadmap. Please try again."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Generation failed: ${e.localizedMessage}",
					roadmap = _uiState.value.roadmap
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
					roadmap = "",
                    targetDate = Date.from(_uiState.value.targetDate.atZone(ZoneId.systemDefault()).toInstant()),
                    priority = _uiState.value.priority,
                    estimatedDuration = _uiState.value.estimatedDuration,
                    createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                    progress = 0.0f
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
					roadmap = "",
                    targetDate = Date.from(_uiState.value.targetDate.atZone(ZoneId.systemDefault()).toInstant()),
                    priority = _uiState.value.priority,
                    estimatedDuration = _uiState.value.estimatedDuration,
                    createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                    progress = 0.0f
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