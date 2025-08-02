
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
                max_tokens = 1000
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
            val prompt = "Create a detailed roadmap for achieving this goal: $goal. Break it down into 5-8 major milestones with specific timeframes."
            val response = generateGoalSuggestions(prompt)
            response?.split("\n")?.filter { it.isNotBlank() && it.length > 10 } ?: generateMockRoadmap(goal)
        } catch (e: Exception) {
            generateMockRoadmap(goal)
        }
    }

    override suspend fun generateDailyTasks(goal: String, roadmapStep: String): List<String> {
        return try {
            val prompt = "For the goal '$goal' and current step '$roadmapStep', suggest 3-5 specific daily tasks that can be completed today."
            val response = generateGoalSuggestions(prompt)
            response?.split("\n")?.filter { it.isNotBlank() && it.length > 5 }?.take(5) ?: generateMockDailyTasks(roadmapStep)
        } catch (e: Exception) {
            generateMockDailyTasks(roadmapStep)
        }
    }

    override suspend fun generateHumorousReminder(taskTitle: String, userName: String): String {
        return try {
            val prompt = "Create a funny, motivational reminder for $userName to complete this task: $taskTitle. Keep it light-hearted and encouraging."
            generateGoalSuggestions(prompt) ?: generateMockHumorousReminder(taskTitle, userName)
        } catch (e: Exception) {
            generateMockHumorousReminder(taskTitle, userName)
        }
    }

    override suspend fun generateRoastMessage(taskTitle: String, userName: String): String {
        return try {
            val prompt = "Create a playful roast message for $userName who didn't complete this task: $taskTitle. Keep it funny but not harsh."
            generateGoalSuggestions(prompt) ?: generateMockRoastMessage(taskTitle, userName)
        } catch (e: Exception) {
            generateMockRoastMessage(taskTitle, userName)
        }
    }

    private fun generateMockResponse(prompt: String): String {
        return when {
            prompt.contains("fitness", ignoreCase = true) || 
            prompt.contains("health", ignoreCase = true) -> {
                "Here's your fitness roadmap:\n\n" +
                "Week 1-2: Start with 20-minute daily walks\n" +
                "Week 3-4: Add bodyweight exercises (push-ups, squats)\n" +
                "Week 5-8: Increase workout intensity and duration\n" +
                "Week 9-12: Join a gym or fitness class\n\n" +
                "Remember to track your progress and stay consistent!"
            }
            
            prompt.contains("career", ignoreCase = true) || 
            prompt.contains("job", ignoreCase = true) -> {
                "Your career development roadmap:\n\n" +
                "Month 1: Update resume and LinkedIn profile\n" +
                "Month 2: Network with industry professionals\n" +
                "Month 3: Apply for relevant positions\n" +
                "Month 4: Prepare for interviews\n" +
                "Month 5-6: Negotiate offers and transition\n\n" +
                "Focus on continuous learning and skill development!"
            }
            
            else -> {
                "Here's your personalized goal roadmap:\n\n" +
                "Phase 1: Planning and preparation\n" +
                "Phase 2: Initial implementation\n" +
                "Phase 3: Building momentum\n" +
                "Phase 4: Optimization and refinement\n" +
                "Phase 5: Achieving your goal\n\n" +
                "Stay focused and celebrate small wins along the way!"
            }
        }
    }

    private fun generateMockRoadmap(goal: String): List<String> {
        return listOf(
            "Phase 1: Research and planning",
            "Phase 2: Set up foundations",
            "Phase 3: Begin implementation",
            "Phase 4: Build consistency",
            "Phase 5: Overcome challenges",
            "Phase 6: Refine approach",
            "Phase 7: Final push to completion"
        )
    }

    private fun generateMockDailyTasks(roadmapStep: String): List<String> {
        return listOf(
            "Spend 15 minutes on planning",
            "Complete one small action step",
            "Review progress from yesterday",
            "Set tomorrow's priorities",
            "Take a 5-minute break to reflect"
        )
    }

    private fun generateMockHumorousReminder(taskTitle: String, userName: String): String {
        val reminders = listOf(
            "Hey $userName! Your task '$taskTitle' is waiting for you like a loyal puppy! 🐕",
            "$userName, your future self called - they want you to complete '$taskTitle' today! 📞",
            "Knock knock! Who's there? Your task '$taskTitle' that's been patiently waiting! 🚪",
            "$userName, your task '$taskTitle' is feeling lonely. Show it some love! ❤️",
            "Breaking news: $userName has the power to complete '$taskTitle' today! 📰"
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
