package com.example.goalguru.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    private val _userSettings = MutableStateFlow(UserSettings(userId = "default_user"))
    val userSettings = _userSettings.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled = _darkModeEnabled.asStateFlow()

    private val _reminderFrequency = MutableStateFlow("daily")
    val reminderFrequency = _reminderFrequency.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            userSettingsRepository.getUserSettings().collectLatest { settings ->
                if (settings != null) {
                    _userSettings.value = settings
                    _notificationsEnabled.value = settings.notificationsEnabled
                    _darkModeEnabled.value = (settings.theme == "DARK")
                    _reminderFrequency.value = settings.reminderTime ?: "09:00"
                }
            }
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            _notificationsEnabled.value = !_notificationsEnabled.value
            updateSettings()
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            _darkModeEnabled.value = !_darkModeEnabled.value
            updateSettings()
        }
    }

    fun setReminderFrequency(frequency: String) {
        viewModelScope.launch {
            _reminderFrequency.value = frequency
            updateSettings()
        }
    }

    private suspend fun updateSettings() {
        val currentSettings = userSettingsRepository.getCurrentSettings()

        val updatedSettings = currentSettings.copy(
                notificationsEnabled = _notificationsEnabled.value,
                theme = if (_darkModeEnabled.value) "DARK" else "LIGHT",
                reminderTime = _reminderFrequency.value
            )

        userSettingsRepository.updateUserSettings(updatedSettings)
    }
}