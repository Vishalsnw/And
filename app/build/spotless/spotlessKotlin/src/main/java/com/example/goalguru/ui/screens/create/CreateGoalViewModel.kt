package com.example.goalguru.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.GoalStatus
import com.example.goalguru.data.model.Priority
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val aiRepository: AIRepository,
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _priority = MutableStateFlow(Priority.MEDIUM)
    val priority: StateFlow<Priority> = _priority.asStateFlow()

    private val _targetDate = MutableStateFlow<Date?>(null)
    val targetDate: StateFlow<Date?> = _targetDate.asStateFlow()

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
        if (newDescription.isNotBlank()) {
            generateSuggestions(newDescription)
        }
    }

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
    }

    fun updatePriority(newPriority: Priority) {
        _priority.value = newPriority
    }

    fun updateTargetDate(newDate: Date?) {
        _targetDate.value = newDate
    }

    private fun generateSuggestions(description: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val suggestions = aiRepository.generateGoalSuggestions(description)
                _suggestions.value = suggestions
            } catch (e: Exception) {
                _suggestions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createGoal(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (_title.value.isBlank()) {
            onError("Title cannot be empty")
            return
        }

        if (_description.value.isBlank()) {
            onError("Description cannot be empty")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    title = _title.value,
                    description = _description.value,
                    category = _category.value.ifBlank { "General" },
                    targetDate = _targetDate.value,
                    priority = _priority.value,
                    status = GoalStatus.ACTIVE,
                    progress = 0f,
                    createdAt = Date(),
                    updatedAt = Date(),
                    roadmap = _suggestions.value,
                )

                goalRepository.insertGoal(goal)
                onSuccess()
            } catch (e: Exception) {
                onError("Failed to create goal: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
