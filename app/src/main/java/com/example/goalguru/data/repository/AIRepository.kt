
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.Message
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor() {

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

    suspend fun generateGoalSuggestions(prompt: String): String? {
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
            null
        }
    }

    suspend fun generateGoalRoadmap(goalTitle: String, goalDescription: String): String? {
        return try {
            val prompt = """
                Create a detailed roadmap for achieving this goal: "$goalTitle"
                
                Description: $goalDescription
                
                Please provide:
                1. 5-7 specific, actionable steps
                2. Estimated timeframe for each step
                3. Key milestones to track progress
                4. Potential challenges and solutions
                
                Format the response as a clear, structured roadmap.
            """.trimIndent()

            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are an expert goal achievement coach. Create detailed, actionable roadmaps."),
                    Message(role = "user", content = prompt)
                ),
                temperature = 0.7,
                max_tokens = 1500
            )

            val response = apiService.generateRoadmap("Bearer $apiKey", request)
            if (response.isSuccessful) {
                response.body()?.choices?.firstOrNull()?.message?.content
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun generateDailyTasks(goalTitle: String, roadmapStep: String): String? {
        return try {
            val prompt = """
                For the goal "$goalTitle", create 3-5 specific daily tasks for this roadmap step:
                "$roadmapStep"
                
                Make each task:
                - Specific and actionable
                - Completable in 15-60 minutes
                - Directly contributing to the goal
                
                Format as a simple numbered list.
            """.trimIndent()

            val request = DeepSeekRequest(
                model = "deepseek-chat",
                messages = listOf(
                    Message(role = "system", content = "You are a productivity expert who creates specific, actionable daily tasks."),
                    Message(role = "user", content = prompt)
                ),
                temperature = 0.6,
                max_tokens = 800
            )

            val response = apiService.generateCompletion("Bearer $apiKey", request)
            response.choices.firstOrNull()?.message?.content
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
