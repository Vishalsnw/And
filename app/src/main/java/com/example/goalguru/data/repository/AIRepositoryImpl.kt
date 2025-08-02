
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.Message
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepositoryImpl @Inject constructor() : AIRepository {

    private val apiKey = "sk-0e8fe679610b4b718e553f4fed7e3792"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deepseek.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(DeepSeekApiService::class.java)

    override suspend fun generateGoalSuggestions(prompt: String): String? {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are a helpful AI goal coach that provides specific, actionable advice."),
                    Message(role = "user", content = prompt)
                ),
                temperature = 0.7,
                maxTokens = 1000
            )
            val response = apiService.generateCompletion("Bearer $apiKey", request)
            response.choices.firstOrNull()?.message?.content
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to mock response
            generateMockResponse(prompt)
        }
    }

    override suspend fun generateRoadmap(goal: String): List<String> {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "Create a detailed step-by-step roadmap for achieving this goal. Provide 5-8 specific, actionable steps."),
                    Message(role = "user", content = "Goal: $goal")
                ),
                temperature = 0.7,
                maxTokens = 1000
            )
            val response = apiService.generateCompletion("Bearer $apiKey", request)
            val content = response.choices.firstOrNull()?.message?.content
            content?.split("\n")?.filter { it.isNotBlank() } ?: generateMockRoadmap(goal)
        } catch (e: Exception) {
            e.printStackTrace()
            generateMockRoadmap(goal)
        }
    }

    override suspend fun generateDailyTasks(goal: String, roadmapStep: String): List<String> {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "Break down this roadmap step into 3-5 daily tasks that are specific and actionable."),
                    Message(role = "user", content = "Goal: $goal\nRoadmap Step: $roadmapStep")
                ),
                temperature = 0.7,
                maxTokens = 500
            )
            val response = apiService.generateCompletion("Bearer $apiKey", request)
            val content = response.choices.firstOrNull()?.message?.content
            content?.split("\n")?.filter { it.isNotBlank() } ?: generateMockDailyTasks(roadmapStep)
        } catch (e: Exception) {
            e.printStackTrace()
            generateMockDailyTasks(roadmapStep)
        }
    }

    override suspend fun generateHumorousReminder(taskTitle: String, userName: String): String {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "Generate a funny, motivational reminder for a user about their task. Keep it light-hearted and encouraging."),
                    Message(role = "user", content = "Task: $taskTitle, User: $userName")
                ),
                temperature = 0.8,
                maxTokens = 100
            )
            val response = apiService.generateCompletion("Bearer $apiKey", request)
            response.choices.firstOrNull()?.message?.content ?: generateMockHumorousReminder(taskTitle, userName)
        } catch (e: Exception) {
            e.printStackTrace()
            generateMockHumorousReminder(taskTitle, userName)
        }
    }

    override suspend fun generateRoastMessage(taskTitle: String, userName: String): String {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "Generate a playful roast message for a user who didn't complete their task. Be funny but not mean."),
                    Message(role = "user", content = "Task: $taskTitle, User: $userName")
                ),
                temperature = 0.9,
                maxTokens = 100
            )
            val response = apiService.generateCompletion("Bearer $apiKey", request)
            response.choices.firstOrNull()?.message?.content ?: generateMockRoastMessage(taskTitle, userName)
        } catch (e: Exception) {
            e.printStackTrace()
            generateMockRoastMessage(taskTitle, userName)
        }
    }

    // Mock/Fallback methods
    private suspend fun generateMockResponse(prompt: String): String {
        delay(1000) // Simulate API call
        return when {
            prompt.contains("learn", ignoreCase = true) -> 
                "Great choice! Learning new skills is always rewarding. Start with small, daily practice sessions."
            prompt.contains("fitness", ignoreCase = true) || prompt.contains("exercise", ignoreCase = true) -> 
                "Awesome fitness goal! Remember, consistency beats intensity. Start with 15-20 minutes daily."
            prompt.contains("business", ignoreCase = true) || prompt.contains("startup", ignoreCase = true) -> 
                "Entrepreneurial spirit! Focus on solving a real problem and start small to validate your idea."
            else -> "Excellent goal! Break it down into smaller, manageable steps and celebrate small wins along the way."
        }
    }

    private fun generateMockRoadmap(goal: String): List<String> {
        return listOf(
            "1. Define specific, measurable objectives",
            "2. Research and gather necessary resources",
            "3. Create a detailed action plan",
            "4. Start with the first small step",
            "5. Track progress and adjust as needed",
            "6. Build consistent daily habits",
            "7. Celebrate milestones and stay motivated"
        )
    }

    private fun generateMockDailyTasks(roadmapStep: String): List<String> {
        return listOf(
            "• Spend 30 minutes on core activity",
            "• Review progress from yesterday",
            "• Plan tomorrow's actions",
            "• Practice or study for 15 minutes",
            "• Connect with others in this field"
        )
    }

    private fun generateMockHumorousReminder(taskTitle: String, userName: String): String {
        val reminders = listOf(
            "Hey $userName! '$taskTitle' is waiting for you like a patient puppy! 🐶",
            "$userName, time to tackle '$taskTitle' - your future self will thank you! 🚀",
            "Knock knock! It's '$taskTitle' reminding $userName it exists! 🚪",
            "$userName, '$taskTitle' misses you! Time for a reunion? 💕",
            "Breaking news: $userName about to crush '$taskTitle'! 📰"
        )
        return reminders.random()
    }

    private fun generateMockRoastMessage(taskTitle: String, userName: String): String {
        val roasts = listOf(
            "$userName, even a sloth would have finished '$taskTitle' by now! 🦥",
            "Hey $userName, your task '$taskTitle' is collecting dust faster than your gym membership! 💪",
            "$userName, '$taskTitle' called - it wants to know if you're ever coming back! 📞",
            "Plot twist: '$taskTitle' wasn't that scary after all, $userName! 👻",
            "$userName, your task '$taskTitle' is starting to think you're a myth! 🦄"
        )
        return roasts.random()
    }
}
