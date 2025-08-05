package com.example.goalguru.ui.screens.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goalguru.data.domain.Goal
import com.example.goalguru.data.domain.GoalRoadmap

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

    val generatedRoadmap by viewModel.generatedRoadmap.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val isCreating by viewModel.isCreating.collectAsState()

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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "AI Goal Assistant",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Describe your goal and I'll help you create a detailed roadmap to achieve it!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = goalInput,
                    onValueChange = { goalInput = it },
                    label = { Text("Describe your goal") },
                    placeholder = { Text("e.g., Learn to play guitar, Run a marathon, Start a business") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                Button(
                    onClick = { viewModel.generateGoalRoadmap(goalInput) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = goalInput.isNotBlank() && !isGenerating,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generating Roadmap...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate AI Roadmap")
                    }
                }
            }

            generatedRoadmap?.let { roadmap ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Generated Roadmap",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = roadmap.roadmap,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Goal Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    OutlinedTextField(
                        value = titleInput.ifEmpty { roadmap.title },
                        onValueChange = { titleInput = it },
                        label = { Text("Goal Title") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                item {
                    OutlinedTextField(
                        value = descriptionInput.ifEmpty { roadmap.description },
                        onValueChange = { descriptionInput = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                item {
                    OutlinedTextField(
                        value = targetDaysInput.ifEmpty { roadmap.targetDays.toString() },
                        onValueChange = { targetDaysInput = it },
                        label = { Text("Target Days") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                if (roadmap.dailyTasks.isNotEmpty()) {
                    item {
                        Text(
                            text = "Daily Tasks",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(roadmap.dailyTasks) { task ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = task,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = {
                            val finalTitle = titleInput.ifEmpty { roadmap.title }
                            val finalDescription = descriptionInput.ifEmpty { roadmap.description }
                            val finalTargetDays = targetDaysInput.ifEmpty { roadmap.targetDays.toString() }.toIntOrNull() ?: roadmap.targetDays

                            viewModel.createGoal(
                                title = finalTitle,
                                description = finalDescription,
                                targetDays = finalTargetDays,
                                roadmap = roadmap.roadmap,
                                dailyTasks = roadmap.dailyTasks,
                                onSuccess = onGoalCreated
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isCreating,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isCreating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Creating Goal...")
                        } else {
                            Text("Create Goal")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// Data class for GoalRoadmap
data class GoalRoadmap(
    val title: String,
    val description: String,
    val targetDays: Int,
    val roadmap: String,
    val dailyTasks: List<String>
)

// Dummy ViewModel for demonstration purposes if not provided by Hilt
// In a real application, this would be provided by Hilt and have actual logic.
class CreateGoalViewModel {
    val generatedRoadmap = androidx.compose.runtime.MutableStateFlow<GoalRoadmap?>(null)
    val isGenerating = androidx.compose.runtime.MutableStateFlow(false)
    val isCreating = androidx.compose.runtime.MutableStateFlow(false)

    fun generateGoalRoadmap(goalDescription: String) {
        isGenerating.value = true
        // Simulate API call or processing
        generatedRoadmap.value = GoalRoadmap(
            title = "Example Goal Title",
            description = "This is a sample description for the goal.",
            targetDays = 30,
            roadmap = "1. Task A\n2. Task B\n3. Task C",
            dailyTasks = listOf("Daily Task 1", "Daily Task 2")
        )
        isGenerating.value = false
    }

    fun createGoal(title: String, description: String, targetDays: Int, roadmap: String, dailyTasks: List<String>, onSuccess: () -> Unit) {
        isCreating.value = true
        // Simulate creating the goal
        println("Creating goal: $title, $description, $targetDays days, roadmap: $roadmap, tasks: $dailyTasks")
        onSuccess()
        isCreating.value = false
    }
}

// Dummy Goal data class for demonstration purposes
data class Goal(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val targetDays: Int = 0,
    val progress: Float = 0f,
    val isCompleted: Boolean = false,
    val dueDate: String? = null,
    val tasks: List<String> = emptyList(),
    val subGoals: List<Goal> = emptyList(),
    val completionDate: String? = null
)

// Dummy ViewModel for Dashboard (if needed for context, not directly modified here)
class DashboardViewModel {
    val goals = androidx.compose.runtime.MutableStateFlow<List<Goal>>(emptyList())
    val isLoading = androidx.compose.runtime.MutableStateFlow(false)

    fun loadGoals() {
        isLoading.value = true
        // Simulate loading goals
        goals.value = listOf(
            Goal(title = "Learn Compose", targetDays = 30, progress = 0.5f, tasks = listOf("Day 1 setup", "Day 5 UI")),
            Goal(title = "Read a book", targetDays = 15, progress = 0.8f, isCompleted = true)
        )
        isLoading.value = false
    }
}

// Dummy GoalDetailScreen (not directly modified here, but needed for compilation context)
@Composable
fun GoalDetailScreen(
    goalId: String,
    onNavigateBack: () -> Unit,
    viewModel: GoalDetailViewModel = hiltViewModel()
) {
    val goal by viewModel.goal.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(goal?.title ?: "Goal Details") },
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (goal == null) {
                Text("Loading goal details...")
            } else {
                Text("Title: ${goal?.title}", fontWeight = FontWeight.Bold)
                Text("Description: ${goal?.description}")
                Text("Target Days: ${goal?.targetDays}")
                Text("Progress: ${(goal?.progress ?: 0f) * 100}%")
                if (goal?.isCompleted == true) {
                    Text("Status: Completed")
                    goal?.completionDate?.let { Text("Completed on: $it") }
                } else {
                    Text("Status: In Progress")
                }

                if (goal?.tasks?.isNotEmpty() == true) {
                    Text("Tasks:", fontWeight = FontWeight.Bold)
                    goal?.tasks?.forEachIndexed { index, task ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Task completed",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(task)
                        }
                    }
                }
                // Placeholder for LinearProgressIndicator
                // LinearProgressIndicator(progress = goal?.progress ?: 0f)
                // Spacer(modifier = Modifier.height(20.dp))
                // Button(onClick = { /* TODO: Handle task completion update */ }) {
                //    Text("Update Task Status")
                // }
            }
        }
    }
}

// Dummy GoalDetailViewModel (if needed for context, not directly modified here)
class GoalDetailViewModel {
    val goal = androidx.compose.runtime.MutableStateFlow<Goal?>(null)

    fun loadGoalDetails(goalId: String) {
        // Simulate fetching goal details
        goal.value = Goal(
            id = goalId,
            title = "Sample Goal Title",
            description = "This is a sample description for the goal.",
            targetDays = 30,
            progress = 0.75f,
            isCompleted = false,
            tasks = listOf("Task 1", "Task 2", "Task 3"),
            completionDate = null
        )
    }
}