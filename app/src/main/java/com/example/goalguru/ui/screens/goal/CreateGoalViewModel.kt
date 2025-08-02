package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.AIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGoalUiState())
    val uiState = _uiState.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    private val _priority = MutableStateFlow("MEDIUM")
    val priority = _priority.asStateFlow()

    private val _targetDate = MutableStateFlow<LocalDateTime?>(null)
    val targetDate = _targetDate.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _aiSuggestions = MutableStateFlow("")
    val aiSuggestions = _aiSuggestions.asStateFlow()

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
        _uiState.value = _uiState.value.copy(category = newCategory)
    }

    fun updatePriority(newPriority: String) {
        _priority.value = newPriority
        _uiState.value = _uiState.value.copy(priority = newPriority)
    }

    fun updateTargetDate(newTargetDate: LocalDateTime?) {
        _targetDate.value = newTargetDate
    }

    fun generateAISuggestions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val prompt = """
                    Help me create a goal with the following details:
                    Title: ${_title.value}
                    Description: ${_description.value}
                    Category: ${_category.value}

                    Please provide:
                    1. An improved description if needed
                    2. Specific milestones to achieve this goal
                    3. Actionable steps to get started
                    4. Tips for staying motivated
                """.trimIndent()

                val suggestions = aiRepository.generateGoalSuggestions(prompt)
                _aiSuggestions.value = suggestions ?: "No suggestions available"
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to generate AI suggestions: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createGoal() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val goalId = UUID.randomUUID().toString()
                val now = LocalDateTime.now()

                val priorityEnum = when(_priority.value) {
                    "LOW" -> Goal.Priority.LOW
                    "MEDIUM" -> Goal.Priority.MEDIUM
                    "HIGH" -> Goal.Priority.HIGH
                    "URGENT" -> Goal.Priority.URGENT
                    else -> Goal.Priority.MEDIUM
                }

                val goal = Goal(
                    id = goalId,
                    userId = "default_user", // TODO: Replace with actual user ID
                    title = _title.value,
                    description = _description.value,
                    category = _category.value.ifEmpty { "General" },
                    priority = priorityEnum,
                    status = Goal.Status.NOT_STARTED,
                    targetDate = _targetDate.value,
                    createdAt = now,
                    updatedAt = now,
                    completedAt = null,
                    progress = 0.0f
                )

                goalRepository.insertGoal(goal)
                _uiState.value = _uiState.value.copy(
                    isGoalCreated = true,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to create goal: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class CreateGoalUiState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val priority: String = "MEDIUM",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isGoalCreated: Boolean = false
)