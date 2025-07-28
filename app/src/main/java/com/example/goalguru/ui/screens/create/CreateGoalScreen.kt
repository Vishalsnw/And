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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    onGoalCreated: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: CreateGoalViewModel = hiltViewModel(),
) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val targetDate by viewModel.targetDate.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create Goal")
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = viewModel::updateTitle,
                    label = { Text("Goal Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = viewModel::updateDescription,
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                )
            }

            item {
                OutlinedTextField(
                    value = targetDate,
                    onValueChange = viewModel::updateTargetDate,
                    label = { Text("Target Date (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
            }

            if (suggestions.isNotEmpty()) {
                item {
                    Text(
                        text = "AI Suggestions",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                }

                items(suggestions) { suggestion ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = suggestion,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.createGoal(onGoalCreated)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && title.isNotBlank() && description.isNotBlank(),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    } else {
                        Text("Create Goal")
                    }
                }
            }
        }
    }
}