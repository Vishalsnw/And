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

    private val apiService: DeepSeekApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeepSeekApiService::class.java)
    }

    suspend fun generateGoalRoadmap(goalTitle: String, goalDescription: String): String? {
        return try {
            val apiKey = System.getenv("DEEPSEEK_API_KEY") ?: return null

            val request = DeepSeekRequest(
                messages = listOf(
                    Message(
                        role = "user",
                        content = "Create a detailed roadmap for achieving this goal: $goalTitle. Description: $goalDescription. Provide specific, actionable steps."
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