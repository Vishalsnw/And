
package com.example.goalguru.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.example.goalguru.data.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _userSettings = MutableStateFlow(
        UserSettings(
            userId = "default",
            notificationsEnabled = true,
            darkModeEnabled = false
        )
    )
    val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    fun toggleNotifications() {
        _userSettings.value = _userSettings.value.copy(
            notificationsEnabled = !_userSettings.value.notificationsEnabled
        )
    }

    fun toggleDarkMode() {
        _userSettings.value = _userSettings.value.copy(
            darkModeEnabled = !_userSettings.value.darkModeEnabled
        )
    }
}
