
package com.example.goalguru.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.User
import com.example.goalguru.data.repository.AIRepository
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _aiSuggestions = MutableStateFlow<String?>(null)
    val aiSuggestions: StateFlow<String?> = _aiSuggestions.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadDashboardData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Load goals
                goalRepository.getAllGoals().collect { goalList ->
                    _goals.value = goalList.sortedByDescending { it.createdAt }
                }
                
                // Load user data
                userRepository.getCurrentUser().collect { user ->
                    _currentUser.value = user
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load dashboard data"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateGoalSuggestions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userGoals = _goals.value
                val prompt = if (userGoals.isEmpty()) {
                    "Suggest 5 inspiring and achievable goals for someone just starting their personal development journey. Include goals for career, health, relationships, skills, and personal growth."
                } else {
                    val existingGoals = userGoals.joinToString(", ") { it.title }
                    "Based on these existing goals: $existingGoals, suggest 3-5 complementary goals that would create a well-rounded personal development plan."
                }

                val suggestions = aiRepository.generateGoalSuggestions(prompt)
                _aiSuggestions.value = suggestions
            } catch (e: Exception) {
                _error.value = "Failed to generate AI suggestions: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateRoadmapForGoal(goal: Goal) {
        viewModelScope.launch {
            try {
                val roadmap = aiRepository.generateGoalRoadmap(goal.title, goal.description)
                if (roadmap != null) {
                    // Update the goal with the generated roadmap
                    val updatedGoal = goal.copy(
                        description = goal.description + "\n\n--- AI Generated Roadmap ---\n" + roadmap,
                        updatedAt = java.time.LocalDateTime.now()
                    )
                    goalRepository.updateGoal(updatedGoal)
                    
                    // Refresh goals
                    loadDashboardData()
                }
            } catch (e: Exception) {
                _error.value = "Failed to generate roadmap: ${e.message}"
            }
        }
    }

    fun generateProgressReport() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val goals = _goals.value
                val totalGoals = goals.size
                val completedGoals = goals.count { it.status == Goal.Status.COMPLETED }
                val inProgressGoals = goals.count { it.status == Goal.Status.IN_PROGRESS }
                val averageProgress = if (goals.isNotEmpty()) {
                    goals.map { it.progress }.average()
                } else 0.0

                val prompt = """
                    Generate a motivational progress report based on these stats:
                    - Total Goals: $totalGoals
                    - Completed Goals: $completedGoals
                    - In Progress Goals: $inProgressGoals
                    - Average Progress: ${(averageProgress * 100).toInt()}%
                    
                    Goals: ${goals.joinToString("; ") { "${it.title} (${(it.progress * 100).toInt()}%)" }}
                    
                    Provide encouragement, highlight achievements, and suggest next steps for continued progress.
                """.trimIndent()

                val report = aiRepository.generateGoalSuggestions(prompt)
                _aiSuggestions.value = report
            } catch (e: Exception) {
                _error.value = "Failed to generate progress report: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearAiSuggestions() {
        _aiSuggestions.value = null
    }
}
