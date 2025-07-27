
package com.example.goalguru.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goalguru.data.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var roastLevel by remember { mutableStateOf(RoastLevel.MILD) }
    var language by remember { mutableStateOf(Language.ENGLISH) }
    var gender by remember { mutableStateOf(Gender.OTHER) }
    var notificationTime by remember { mutableStateOf("09:00") }
    var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                SettingsSection(title = "Notifications") {
                    SettingsSwitchItem(
                        title = "Daily Notifications",
                        description = "Receive daily reminders and motivation",
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                    
                    SettingsDropdownItem(
                        title = "Roast Level",
                        description = "How motivational should we be?",
                        selectedValue = roastLevel.displayName,
                        options = RoastLevel.values().map { it.displayName },
                        onSelectionChange = { selectedName ->
                            roastLevel = RoastLevel.values().first { it.displayName == selectedName }
                        }
                    )
                    
                    SettingsTimePickerItem(
                        title = "Notification Time",
                        description = "When to send daily reminders",
                        selectedTime = notificationTime,
                        onTimeChange = { notificationTime = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Personalization") {
                    SettingsDropdownItem(
                        title = "Language",
                        description = "Choose your preferred language",
                        selectedValue = language.displayName,
                        options = Language.values().map { it.displayName },
                        onSelectionChange = { selectedName ->
                            language = Language.values().first { it.displayName == selectedName }
                        }
                    )
                    
                    SettingsDropdownItem(
                        title = "Gender",
                        description = "For personalized messages",
                        selectedValue = gender.displayName,
                        options = Gender.values().map { it.displayName },
                        onSelectionChange = { selectedName ->
                            gender = Gender.values().first { it.displayName == selectedName }
                        }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Appearance") {
                    SettingsDropdownItem(
                        title = "Theme",
                        description = "Choose your preferred theme",
                        selectedValue = themeMode.displayName,
                        options = ThemeMode.values().map { it.displayName },
                        onSelectionChange = { selectedName ->
                            themeMode = ThemeMode.values().first { it.displayName == selectedName }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsDropdownItem(
    title: String,
    description: String,
    selectedValue: String,
    options: List<String>,
    onSelectionChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            TextButton(onClick = { expanded = true }) {
                Text(selectedValue)
            }
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelectionChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsTimePickerItem(
    title: String,
    description: String,
    selectedTime: String,
    onTimeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        TextButton(
            onClick = { 
                // TODO: Open time picker dialog
            }
        ) {
            Text(selectedTime)
        }
    }
}
