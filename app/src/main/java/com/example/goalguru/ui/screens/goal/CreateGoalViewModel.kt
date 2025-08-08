package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.model.Priority
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.AIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

data class CreateGoalUiState(
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val generatedRoadmap: List<String> = emptyList(),
    val isGoalCreated: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGoalUiState())
    val uiState: StateFlow<CreateGoalUiState> = _uiState.asStateFlow()


    fun generateGoalRoadmap(goalTitle: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true)
            try {
                val roadmap = aiRepository.generateRoadmap(goalTitle)
                _uiState.value = _uiState.value.copy(
                    generatedRoadmap = roadmap,
                    isGenerating = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isGenerating = false)
            }
        }
    }

    fun createGoal(
        title: String,
        description: String,
        targetDays: Int,
        roadmap: List<String>,
        dailyTasks: List<String>,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val goalId = UUID.randomUUID().toString()
                val goal = Goal(
                    id = goalId,
                    title = title,
                    description = description,
                    targetDays = targetDays,
                    roadmap = roadmap,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )

                goalRepository.insertGoal(goal)
                _uiState.value = _uiState.value.copy(isGoalCreated = true)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun createGoalWithTasks(
        title: String,
        description: String,
        targetDays: Int,
        tasks: List<String>
    ) {
        viewModelScope.launch {
            _isCreating.value = true
            val goal = Goal(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                targetDays = targetDays,
                progress = 0f,
                createdAt = LocalDateTime.now(),
                roadmap = tasks,
                priority = Goal.Priority.MEDIUM
            )
            goalRepository.insertGoal(goal)
            _isCreating.value = false
        }
    }

    private val _isCreating = MutableStateFlow(false)
    val isCreating: StateFlow<Boolean> = _isCreating.asStateFlow()
}