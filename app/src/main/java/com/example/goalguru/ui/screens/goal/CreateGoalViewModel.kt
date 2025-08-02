
package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.firebase.FirebaseConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class CreateGoalUiState(
    val isGenerating: Boolean = false,
    val generatedGoal: Goal? = null,
    val error: String? = null
)

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val aiRepository: AIRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGoalUiState())
    val uiState: StateFlow<CreateGoalUiState> = _uiState.asStateFlow()

    fun generateGoalRoadmap(goalInput: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, error = null)
            
            try {
                val prompt = """
                    Create a structured goal plan for: "$goalInput"
                    
                    Please provide:
                    1. A clear, specific title for this goal
                    2. A detailed description explaining the goal and its benefits
                    3. Key milestones or steps to achieve this goal
                    4. Estimated timeline
                    
                    Format the response as a structured plan that motivates and guides the user.
                """.trimIndent()
                
                val aiResponse = aiRepository.generateGoalSuggestions(prompt)
                
                if (aiResponse != null) {
                    val goal = parseAIResponseToGoal(goalInput, aiResponse)
                    _uiState.value = _uiState.value.copy(
                        isGenerating = false,
                        generatedGoal = goal
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isGenerating = false,
                        error = "Failed to generate goal roadmap. Please try again."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "Error: ${e.message}"
                )
            }
            onComplete()
        }
    }

    fun saveGoal() {
        viewModelScope.launch {
            val goal = _uiState.value.generatedGoal
            if (goal != null) {
                try {
                    goalRepository.insertGoal(goal)
                    clearGenerated()
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to save goal: ${e.message}"
                    )
                }
            }
        }
    }

    fun clearGenerated() {
        _uiState.value = _uiState.value.copy(
            generatedGoal = null,
            error = null
        )
    }

    private fun parseAIResponseToGoal(originalInput: String, aiResponse: String): Goal {
        val currentUser = FirebaseConfig.auth.currentUser
        val userId = currentUser?.uid ?: "anonymous"
        
        // Extract title from AI response or use original input
        val title = extractTitle(aiResponse) ?: originalInput.take(50)
        
        return Goal(
            id = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            description = aiResponse,
            status = Goal.Status.IN_PROGRESS,
            progress = 0,
            createdAt = Date(),
            updatedAt = Date(),
            targetDate = calculateTargetDate(aiResponse)
        )
    }

    private fun extractTitle(aiResponse: String): String? {
        // Try to extract a title from the AI response
        val lines = aiResponse.split("\n")
        return lines.firstOrNull { line ->
            line.trim().isNotEmpty() && 
            (line.contains("Goal:") || line.contains("Title:") || line.length < 100)
        }?.replace("Goal:", "")?.replace("Title:", "")?.trim()
    }

    private fun calculateTargetDate(aiResponse: String): Date? {
        // Simple extraction of timeline from AI response
        val calendar = Calendar.getInstance()
        
        when {
            aiResponse.contains("month", ignoreCase = true) -> {
                val months = extractNumber(aiResponse, "month") ?: 3
                calendar.add(Calendar.MONTH, months)
            }
            aiResponse.contains("week", ignoreCase = true) -> {
                val weeks = extractNumber(aiResponse, "week") ?: 4
                calendar.add(Calendar.WEEK_OF_YEAR, weeks)
            }
            aiResponse.contains("day", ignoreCase = true) -> {
                val days = extractNumber(aiResponse, "day") ?: 30
                calendar.add(Calendar.DAY_OF_YEAR, days)
            }
            else -> {
                calendar.add(Calendar.MONTH, 3) // Default 3 months
            }
        }
        
        return calendar.time
    }

    private fun extractNumber(text: String, unit: String): Int? {
        val regex = "\\b(\\d+)\\s*$unit".toRegex(RegexOption.IGNORE_CASE)
        return regex.find(text)?.groups?.get(1)?.value?.toIntOrNull()
    }
}
