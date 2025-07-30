package com.example.goalguru.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val userSettings by viewModel.userSettings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingCategoryCard(
                    title = "Notifications",
                    settings = listOf(
                        SettingItem(
                            name = "Enable Notifications",
                            isEnabled = userSettings.notificationsEnabled,
                            onToggle = { viewModel.updateNotificationSettings(it) }
                        )
                    )
                )
            }

            item {
                SettingCategoryCard(
                    title = "Appearance",
                    settings = listOf(
                        SettingItem(
                            name = "Dark Mode",
                            isEnabled = userSettings.darkModeEnabled,
                            onToggle = { viewModel.updateDarkMode(it) }
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun SettingCategoryCard(
    title: String,
    settings: List<SettingItem>,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            settings.forEach { setting ->
                SettingToggleRow(
                    name = setting.name,
                    isEnabled = setting.isEnabled,
                    onToggle = setting.onToggle
                )
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    name: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle
        )
    }
}

private data class SettingItem(
    val name: String,
    val isEnabled: Boolean,
    val onToggle: (Boolean) -> Unit
)
