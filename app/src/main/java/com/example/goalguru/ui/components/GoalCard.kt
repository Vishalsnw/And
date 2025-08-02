package com.example.goalguru.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.goalguru.data.model.Goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalCard(
    goal: Goal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = goal.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = goal.priority.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = when (goal.priority) {
                        com.example.goalguru.data.model.Priority.HIGH -> MaterialTheme.colorScheme.error
                        com.example.goalguru.data.model.Priority.MEDIUM -> MaterialTheme.colorScheme.primary
                        com.example.goalguru.data.model.Priority.LOW -> MaterialTheme.colorScheme.secondary
                        com.example.goalguru.data.model.Priority.OPTIONAL -> MaterialTheme.colorScheme.outline
                    }
                )
            }

            Text(
                text = goal.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = goal.progress,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${(goal.progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(
                text = goal.status.name.replace("_", " "),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}