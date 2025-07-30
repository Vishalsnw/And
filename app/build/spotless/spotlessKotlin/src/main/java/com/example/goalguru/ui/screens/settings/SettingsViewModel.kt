package com.example.goalguru.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userSettings = MutableStateFlow(UserSettings())
    val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadUserSettings()
    }

    private fun loadUserSettings() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.getUserSettings().collect { settings ->
                    _userSettings.value = settings
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateNotificationSettings(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _userSettings.value.copy(notificationsEnabled = enabled)
            _userSettings.value = updatedSettings
            userRepository.saveUserSettings(updatedSettings)
        }
    }

    fun updateDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _userSettings.value.copy(darkModeEnabled = enabled)
            _userSettings.value = updatedSettings
            userRepository.saveUserSettings(updatedSettings)
        }
    }
}
