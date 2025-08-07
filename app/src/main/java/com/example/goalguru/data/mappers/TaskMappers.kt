
package com.example.goalguru.data.mappers

import com.example.goalguru.data.database.entities.TaskEntity
import com.example.goalguru.data.model.DailyTask

fun TaskEntity.toDailyTask(): DailyTask {
    return DailyTask(
        id = id,
        goalId = goalId,
        title = title,
        description = description,
        date = date,
        isCompleted = isCompleted,
        completedAt = completedAt,
        createdAt = createdAt
    )
}

fun DailyTask.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        goalId = goalId,
        title = title,
        description = description,
        date = date,
        isCompleted = isCompleted,
        completedAt = completedAt,
        createdAt = createdAt
    )
}
