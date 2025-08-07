package com.example.goalguru.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goalguru.ui.screens.goal.CreateGoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    onBackPressed: () -> Unit,
    onGoalCreated: () -> Unit,
    viewModel: CreateGoalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.isLoading

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var targetDays by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isGoalCreated) {
        if (uiState.isGoalCreated) {
            onGoalCreated()
        }
    }

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
                value = title,
                onValueChange = { title = it },
                label = { Text("Goal Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Goal Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = targetDays,
                onValueChange = { targetDays = it },
                label = { Text("Target Days") },
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.generatedRoadmap.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Generated Roadmap",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn {
                            items(uiState.generatedRoadmap) { step ->
                                Text(
                                    text = "• $step",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val days = targetDays.toIntOrNull() ?: 30
                    viewModel.createGoalWithTasks(
                        title = title,
                        description = description,
                        targetDays = days,
                        tasks = uiState.generatedRoadmap
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && title.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Create Goal")
                }
            }
        }
    }
}