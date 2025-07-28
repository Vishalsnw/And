package com.example.goalguru.ui.screens.create

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
import java.util.*
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

    private val _targetDate = MutableStateFlow("")
    val targetDate: StateFlow<String> = _targetDate.asStateFlow()

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
        generateSuggestions(newDescription)
    }

    fun updateTargetDate(newDate: String) {
        _targetDate.value = newDate
    }

    private fun generateSuggestions(description: String) {
        if (description.length > 10) {
            viewModelScope.launch {
                try {
                    val suggestions = aiRepository.generateGoalSuggestions(description)
                    _suggestions.value = suggestions
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }
    }

    fun createGoal(onSuccess: () -> Unit) {
        if (_title.value.isBlank() || _description.value.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    title = _title.value,
                    description = _description.value,
                    targetDate = _targetDate.value,
                    progress = 0f,
                    isCompleted = false,
                    createdAt = System.currentTimeMillis()
                )

                goalRepository.insertGoal(goal)
                onSuccess()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
package com.example.goalguru.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    fun createGoal(title: String, description: String, targetDate: String) {
        viewModelScope.launch {
            try {
                val goal = Goal(
                    id = "",
                    title = title.trim(),
                    description = description.trim(),
                    targetDate = targetDate,
                    createdAt = System.currentTimeMillis(),
                    progress = 0f,
                    isCompleted = false,
                    tasks = emptyList()
                )
                goalRepository.createGoal(goal)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
