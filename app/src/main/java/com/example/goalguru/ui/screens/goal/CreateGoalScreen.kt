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
    onNavigateBack: () -> Unit
) {
    var goalTitle by remember { mutableStateOf("") }
    var goalDescription by remember { mutableStateOf("") }
    var targetDays by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Create New Goal",
                        fontWeight = FontWeight.Bold
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // TODO: Save goal logic
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = goalTitle.isNotEmpty() && goalDescription.isNotEmpty() && targetDays.isNotEmpty()
            ) {
                Text("Create Goal")
            }
        }
    }
}