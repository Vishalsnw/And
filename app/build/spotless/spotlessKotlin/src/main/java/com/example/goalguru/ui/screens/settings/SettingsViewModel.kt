
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

data class SettingsUiState(
    val deepSeekApiKey: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val notificationsEnabled: Boolean = true,
    val notificationStyle: String = "Mild",
    val isLoading: Boolean = false,
    val message: String = "",
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val settings = userRepository.getUserSettings()
                _uiState.value = _uiState.value.copy(
                    deepSeekApiKey = settings.deepSeekApiKey,
                    name = settings.name,
                    age = settings.age,
                    gender = settings.gender,
                    notificationsEnabled = settings.notificationsEnabled,
                    notificationStyle = settings.notificationStyle,
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Failed to load settings",
                )
            }
        }
    }

    fun updateDeepSeekApiKey(apiKey: String) {
        _uiState.value = _uiState.value.copy(deepSeekApiKey = apiKey)
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateAge(age: Int) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun updateGender(gender: String) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(notificationsEnabled = enabled)
    }

    fun updateNotificationStyle(style: String) {
        _uiState.value = _uiState.value.copy(notificationStyle = style)
    }

    fun saveSettings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = "")

            try {
                val currentState = _uiState.value
                val settings = UserSettings(
                    deepSeekApiKey = currentState.deepSeekApiKey,
                    name = currentState.name,
                    age = currentState.age,
                    gender = currentState.gender,
                    notificationsEnabled = currentState.notificationsEnabled,
                    notificationStyle = currentState.notificationStyle,
                )

                userRepository.saveUserSettings(settings)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Settings saved successfully!",
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Failed to save settings: ${e.message}",
                )
            }
        }
    }
}
