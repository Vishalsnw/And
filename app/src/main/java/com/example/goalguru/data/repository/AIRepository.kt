
package com.example.goalguru.data.repository

interface AIRepository {
    suspend fun generateGoalSuggestions(prompt: String): String?
    suspend fun generateRoadmap(goal: String): List<String>
    suspend fun generateDailyTasks(goal: String, roadmapStep: String): List<String>
    suspend fun generateHumorousReminder(taskTitle: String, userName: String): String
    suspend fun generateRoastMessage(taskTitle: String, userName: String): String
}
