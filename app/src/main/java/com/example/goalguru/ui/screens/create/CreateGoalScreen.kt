package com.example.goalguru.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    onGoalCreated: () -> Unit,
    onBackPressed: () -> Unit
) {
    var goalTitle by remember { mutableStateOf("") }
    var goalDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Goal") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
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
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = goalDescription,
                onValueChange = { goalDescription = it },
                label = { Text("Goal Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoalCreated,
                modifier = Modifier.fillMaxWidth(),
                enabled = goalTitle.isNotBlank()
            ) {
                Text("Create Goal")
            }
        }
    }
}