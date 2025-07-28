package com.example.goalguru.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goalguru.data.model.Goal
import com.example.goalguru.data.model.Priority

@Composable
fun GoalCard(
    goal: Goal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.titleMedium,
            )

            if (goal.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = goal.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = goal.progress,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${(goal.progress * 100).toInt()}% Complete",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun PriorityChip(priority: Priority) {
    val (color, text) = when (priority) {
        Priority.LOW -> Pair(MaterialTheme.colorScheme.outline, "Low")
        Priority.MEDIUM -> Pair(MaterialTheme.colorScheme.secondary, "Medium")
        Priority.HIGH -> Pair(MaterialTheme.colorScheme.tertiary, "High")
        Priority.URGENT -> Pair(MaterialTheme.colorScheme.error, "Urgent")
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f),
        modifier = Modifier.padding(start = 8.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium,
        )
    }
}
