
package com.example.goalguru.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.GoalStatus
import com.example.goalguru.data.model.Priority
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class CreateGoalUiState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val priority: Priority = Priority.MEDIUM,
    val targetDate: Date? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val aiRepository: AIRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateGoalUiState())
    val uiState: StateFlow<CreateGoalUiState> = _uiState.asStateFlow()
    
    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }
    
    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }
    
    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }
    
    fun updatePriority(priority: Priority) {
        _uiState.value = _uiState.value.copy(priority = priority)
    }
    
    fun updateTargetDate(date: Date) {
        _uiState.value = _uiState.value.copy(targetDate = date)
    }
    
    fun showDatePicker() {
        // Date picker implementation would go here
        // For now, set a default date
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        updateTargetDate(calendar.time)
    }
    
    fun createGoal() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            
            try {
                val currentState = _uiState.value
                val goal = Goal(
                    id = UUID.randomUUID().toString(),
                    title = currentState.title,
                    description = currentState.description,
                    category = currentState.category,
                    targetDate = currentState.targetDate ?: Date(),
                    priority = currentState.priority,
                    status = GoalStatus.ACTIVE
                )
                
                // Save goal
                goalRepository.insertGoal(goal)
                
                // Generate roadmap
                val userSettings = userRepository.getUserSettings()
                if (userSettings.deepSeekApiKey.isNotEmpty()) {
                    val roadmapResult = aiRepository.generateRoadmap(
                        goal.title,
                        goal.description,
                        userSettings.deepSeekApiKey
                    )
                    
                    roadmapResult.onSuccess { roadmap ->
                        val updatedGoal = goal.copy(roadmap = roadmap)
                        goalRepository.updateGoal(updatedGoal)
                    }
                }
                
                _uiState.value = _uiState.value.copy(isLoading = false)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to create goal: ${e.message}"
                )
            }
        }
    }
}
