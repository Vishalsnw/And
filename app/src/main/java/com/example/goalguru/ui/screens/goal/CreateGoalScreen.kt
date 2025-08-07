
package com.example.goalguru.ui.screens.goal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    onNavigateBack: () -> Unit,
    onGoalCreated: () -> Unit,
    viewModel: CreateGoalViewModel = hiltViewModel()
) {
    var goalInput by remember { mutableStateOf("") }
    var titleInput by remember { mutableStateOf("") }
    var descriptionInput by remember { mutableStateOf("") }
    var targetDaysInput by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isGenerating = uiState.isGenerating
    val isCreating by viewModel.isCreating.collectAsState()

    LaunchedEffect(uiState.isGoalCreated) {
        if (uiState.isGoalCreated) {
            onGoalCreated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Goal") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Let's create your goal!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                OutlinedTextField(
                    value = goalInput,
                    onValueChange = { goalInput = it },
                    label = { Text("Describe your goal") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }

            item {
                Button(
                    onClick = {
                        if (goalInput.isNotBlank()) {
                            viewModel.generateGoalRoadmap(goalInput)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isGenerating && goalInput.isNotBlank()
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(if (isGenerating) "Generating..." else "Generate Goal Plan")
                }
            }

            if (uiState.generatedRoadmap.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Generated Plan",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            uiState.generatedRoadmap.forEachIndexed { index, step ->
                                Text(
                                    text = "${index + 1}. $step",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text("Goal Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = descriptionInput,
                        onValueChange = { descriptionInput = it },
                        label = { Text("Goal Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }

                item {
                    OutlinedTextField(
                        value = targetDaysInput,
                        onValueChange = { targetDaysInput = it },
                        label = { Text("Target Days") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                item {
                    Button(
                        onClick = {
                            val days = targetDaysInput.toIntOrNull() ?: 30
                            viewModel.createGoalWithTasks(
                                title = titleInput,
                                description = descriptionInput,
                                targetDays = days,
                                tasks = uiState.generatedRoadmap
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isCreating && titleInput.isNotBlank()
                    ) {
                        if (isCreating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(if (isCreating) "Creating..." else "Create Goal")
                    }
                }
            }

            uiState.errorMessage?.let { error ->
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = error,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}
