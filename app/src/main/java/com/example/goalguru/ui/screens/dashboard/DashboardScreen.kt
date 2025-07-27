package com.example.goalguru.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goalguru.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToCreateGoal: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateGoal
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProgressCard(
                    title = "Overall Progress",
                    progress = 65f,
                    subtitle = "Keep going!"
                )
            }

            item {
                TodayTaskCard(
                    title = "Today's Focus",
                    tasks = listOf(
                        "Complete Kotlin lesson",
                        "30-min workout",
                        "Read 20 pages"
                    )
                )
            }

            item {
                Text(
                    text = "Your Goals",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(sampleGoals) { goal ->
                GoalCard(
                    title = goal.title,
                    description = goal.description,
                    progress = goal.progress,
                    daysLeft = goal.daysLeft,
                    onClick = { }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

private val sampleGoals = listOf(
    SampleGoal("Learn Kotlin", "Master Android development with Kotlin", 40f, 25),
    SampleGoal("Lose 5kg", "Get fit and healthy", 20f, 45),
    SampleGoal("Read 12 Books", "Expand knowledge and skills", 75f, 120)
)

private data class SampleGoal(
    val title: String,
    val description: String,
    val progress: Float,
    val daysLeft: Int
)