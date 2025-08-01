package com.example.goalguru.ui.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {
    // Empty for now - just to test if kapt works
}
