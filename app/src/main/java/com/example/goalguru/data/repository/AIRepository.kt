package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deepseek.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(DeepSeekApiService::class.java)

    suspend fun generateGoalSuggestions(prompt: String): String? {
        return try {
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are a helpful AI goal coach."),
                    Message(role = "user", content = prompt)
                ),
                temperature = 0.7
            )
            val response = apiService.generateCompletion(request)
            response.choices.firstOrNull()?.message?.content
        } catch (e: Exception) {
            null
        }
    }

    suspend fun generateGoalRoadmap(goalTitle: String, goalDescription: String): String? {
        return try {
            val apiKey = System.getenv("DEEPSEEK_API_KEY") ?: return null

            val request = DeepSeekRequest(
                messages = listOf(
                    Message(
                        role = "user",
                        content = "Create a detailed roadmap for achieving this goal: $goalTitle. " +
                            "Description: $goalDescription. Provide specific, actionable steps."
                    )
                )
            )

            val response = apiService.generateRoadmap("Bearer $apiKey", request)
            if (response.isSuccessful) {
                response.body()?.choices?.firstOrNull()?.message?.content
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}