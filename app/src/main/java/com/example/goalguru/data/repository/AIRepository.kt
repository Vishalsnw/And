
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.Message
import com.google.gson.Gson
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
            val apiKey = System.getenv("DEEPSEEK_API_KEY") ?: "sk-0e8fe679610b4b718e553f4fed7e3792"
            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are a helpful AI goal coach. Provide practical and achievable goal suggestions."),
                    Message(role = "user", content = prompt),
                ),
                temperature = 0.7,
                max_tokens = 500
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

    suspend fun generateGoalRoadmap(goalTitle: String, goalDescription: String): String? {
        return try {
            val apiKey = System.getenv("DEEPSEEK_API_KEY") ?: "sk-0e8fe679610b4b718e553f4fed7e3792"
            
            val prompt = """
                Create a detailed step-by-step roadmap for achieving this goal:
                
                Goal: $goalTitle
                Description: $goalDescription
                
                Please provide:
                1. Clear, actionable steps
                2. Estimated timeframes for each step
                3. Potential challenges and solutions
                4. Milestones to track progress
                
                Format the response as a structured roadmap with numbered steps.
            """.trimIndent()

            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(
                        role = "system", 
                        content = "You are an expert goal coach. Create detailed, practical roadmaps that break down goals into achievable steps."
                    ),
                    Message(role = "user", content = prompt),
                ),
                temperature = 0.7,
                max_tokens = 1000
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

    suspend fun generateTaskSuggestions(goalTitle: String, currentProgress: String): String? {
        return try {
            val apiKey = System.getenv("DEEPSEEK_API_KEY") ?: "sk-0e8fe679610b4b718e553f4fed7e3792"
            
            val prompt = """
                Based on the goal "$goalTitle" and current progress: "$currentProgress",
                suggest 3-5 specific tasks that should be completed next.
                
                Make the tasks:
                - Specific and actionable
                - Achievable within 1-2 weeks
                - Clearly defined with measurable outcomes
                
                Format as a numbered list.
            """.trimIndent()

            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are a productivity coach. Suggest specific, actionable tasks."),
                    Message(role = "user", content = prompt),
                ),
                temperature = 0.7,
                max_tokens = 300
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
