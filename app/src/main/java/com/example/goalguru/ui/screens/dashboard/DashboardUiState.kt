
package com.example.goalguru.ui.screens.dashboard

import com.example.goalguru.data.model.Goal

data class DashboardUiState(
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
