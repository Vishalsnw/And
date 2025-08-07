
package com.example.goalguru.data.mappers

import com.example.goalguru.data.database.entities.GoalEntity
import com.example.goalguru.data.model.Goal

fun GoalEntity.toGoal(): Goal {
    return Goal(
        id = id,
        title = title,
        description = description,
        targetDays = targetDays,
        currentProgress = currentProgress,
        status = Goal.Status.valueOf(status),
        createdAt = createdAt,
        updatedAt = updatedAt,
        roadmap = roadmap,
        completedAt = completedAt,
        priority = Goal.Priority.valueOf(priority),
        progress = progress,
        estimatedDuration = estimatedDuration
    )
}

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        title = title,
        description = description,
        targetDays = targetDays,
        currentProgress = currentProgress,
        status = status.name,
        createdAt = createdAt,
        updatedAt = updatedAt,
        roadmap = roadmap,
        completedAt = completedAt,
        priority = priority.name,
        progress = progress,
        estimatedDuration = estimatedDuration
    )
}
