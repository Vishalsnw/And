package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.DailyTask
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _isCreating = MutableStateFlow(false)
    val isCreating: StateFlow<Boolean> = _isCreating.asStateFlow()

    fun createGoal(
        title: String,
        description: String,
        targetDays: Int,
        roadmap: List<String>,
        dailyTasks: List<String>,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isCreating.value = true
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
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isCreating.value = false
            }
        }
    }
}