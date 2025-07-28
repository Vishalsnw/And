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
    private val userRepository: UserRepository
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
            try {
                _isLoading.value = true
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

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val updatedSettings = _userSettings.value.copy(notificationsEnabled = enabled)
                userRepository.updateUserSettings(updatedSettings)
                _userSettings.value = updatedSettings
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val updatedSettings = _userSettings.value.copy(darkModeEnabled = enabled)
                userRepository.updateUserSettings(updatedSettings)
                _userSettings.value = updatedSettings
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateReminderTime(time: String) {
        viewModelScope.launch {
            try {
                val updatedSettings = _userSettings.value.copy(reminderTime = time)
                userRepository.updateUserSettings(updatedSettings)
                _userSettings.value = updatedSettings
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadUserSettings()
    }

    private fun loadUserSettings() {
        viewModelScope.launch {
            userRepository.getUserSettings().collect { settings ->
                _uiState.value = _uiState.value.copy(
                    userSettings = settings,
                    isLoading = false
                )
            }
        }
    }

    fun updateSettings(settings: UserSettings) {
        viewModelScope.launch {
            try {
                userRepository.updateUserSettings(settings)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }
}

data class SettingsUiState(
    val userSettings: UserSettings = UserSettings(),
    val isLoading: Boolean = true,
    val error: String? = null
)
