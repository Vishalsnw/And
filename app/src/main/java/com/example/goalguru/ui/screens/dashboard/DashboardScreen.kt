The code has compilation errors due to missing closing braces in DashboardScreen.kt and CreateGoalScreen.kt, which will be fixed in this version.
```

```kotlin
package com.example.goalguru.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goalguru.ui.components.ProgressCard
import com.example.goalguru.ui.components.TodayTaskCard
import com.example.goalguru.ui.components.GoalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToCreateGoal: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GoalGuru") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Today's Task",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
fun DashboardScreen(
    onCreateGoal: () -> Unit,
    onSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "GoalGuru",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateGoal,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Goal")
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
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Today's Focus",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                TodayTaskCard(
                    taskTitle = "Practice Kotlin for 30 minutes",
                    goalTitle = "Learn Kotlin Development",
                    onComplete = { /* TODO: Implement */ },
                    onSkip = { /* TODO: Implement */ }
                )
            }

            item {
                Text(
                    text = "Your Progress",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                ProgressCard(
                    totalTasks = 30,
                    completedTasks = 12,
                    currentStreak = 5,
                    bestStreak = 8,
                    completionPercentage = 40f
                )
            }

            item {
                Text(
                    text = "Active Goals",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Sample goals - replace with actual data
            items(sampleGoals) { goal ->
                GoalCard(
                    title = goal.title,
                    description = goal.description,
                    progress = goal.progress,
                    daysLeft = goal.daysLeft,
                    onClick = { /* TODO: Navigate to goal details */ }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
            }
        }
    }
}

// Sample data - replace with actual data from ViewModel
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
```The code has compilation errors due to missing closing braces in DashboardScreen.kt and CreateGoalScreen.kt, which will be fixed in this version.
`