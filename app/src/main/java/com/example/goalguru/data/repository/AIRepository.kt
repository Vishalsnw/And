
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.api.DeepSeekRequest
import com.example.goalguru.data.api.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor() {
    
    private val deepSeekService: DeepSeekApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeepSeekApiService::class.java)
    }

    suspend fun generateGoalSuggestions(description: String): List<String> {
        return try {
            val apiKey = System.getenv("DEEPSEEK_API_KEY")
            if (apiKey.isNullOrEmpty()) {
                return getFallbackSuggestions(description)
            }

            val request = DeepSeekRequest(
                messages = listOf(
                    Message(
                        role = "user",
                        content = "Generate 4 specific, actionable suggestions for achieving this goal: $description"
                    )
                )
            )

            val response = deepSeekService.generateRoadmap("Bearer $apiKey", request)
            if (response.isSuccessful && response.body() != null) {
                val content = response.body()!!.choices.firstOrNull()?.message?.content
                content?.split("\n")?.filter { it.isNotBlank() }?.take(4) ?: getFallbackSuggestions(description)
            } else {
                getFallbackSuggestions(description)
            }
        } catch (e: Exception) {
            getFallbackSuggestions(description)
        }
    }

    private fun getFallbackSuggestions(description: String): List<String> {
        val suggestions = mutableListOf<String>()
        when {
            description.contains("fitness", ignoreCase = true) -> {
                suggestions.addAll(
                    listOf(
                        "Start with 15-minute daily workouts",
                        "Track your daily step count",
                        "Set weekly weight or measurement goals",
                        "Plan healthy meal prep sessions",
                    ),
                )
            }
            description.contains("learning", ignoreCase = true) -> {
                suggestions.addAll(
                    listOf(
                        "Dedicate 30 minutes daily to study",
                        "Set weekly learning milestones",
                        "Practice regularly with hands-on exercises",
                        "Join online communities for support",
                    ),
                )
            }
            else -> {
                suggestions.addAll(
                    listOf(
                        "Break down your goal into smaller, manageable tasks",
                        "Set specific deadlines for each milestone",
                        "Track your daily progress",
                        "Celebrate small wins along the way",
                    ),
                )
            }
        }
        return suggestions
    }
}
