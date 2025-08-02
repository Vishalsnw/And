
package com.example.goalguru.data.model

data class DashboardUiState(
    val isLoading: Boolean = false,
    val goals: List<Goal> = emptyList(),
    val error: String? = null,
    val totalGoals: Int = 0,
    val completedGoals: Int = 0,
    val activeGoals: Int = 0
)
