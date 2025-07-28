package com.example.goalguru.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.GoalStatus
import com.example.goalguru.data.model.Priority
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {

    fun createGoal(title: String, description: String) {
        viewModelScope.launch {
            val goal = Goal(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                status = GoalStatus.ACTIVE,
                priority = Priority.MEDIUM,
                createdAt = Date(),
                updatedAt = Date(),
                completedAt = null,
                tasks = emptyList(),
            )
            goalRepository.insertGoal(goal)
        }
    }
}