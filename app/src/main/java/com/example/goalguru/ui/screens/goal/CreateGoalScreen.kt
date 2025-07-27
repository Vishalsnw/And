package com.example.goalguru.ui.screens.goal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    onNavigateBack: () -> Unit,
    onGoalCreated: () -> Unit
) {
    var goalTitle by remember { mutableStateOf("") }
    var goalDescription by remember { mutableStateOf("") }
    var targetDays by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Personal") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Create New Goal",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = goalTitle,
                onValueChange = { goalTitle = it },
                label = { Text("Goal Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = goalDescription,
                onValueChange = { goalDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            OutlinedTextField(
                value = targetDays,
                onValueChange = { targetDays = it },
                label = { Text("Target Days") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (goalTitle.isNotBlank() && goalDescription.isNotBlank() && targetDays.isNotBlank()) {
                        // TODO: Save goal to database
                        onGoalCreated()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = goalTitle.isNotBlank() && goalDescription.isNotBlank() && targetDays.isNotBlank()
            ) {
                Text("Create Goal")
            }

            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}