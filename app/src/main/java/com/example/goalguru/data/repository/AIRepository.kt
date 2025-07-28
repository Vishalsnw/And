
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.api.DeepSeekRequest
import com.example.goalguru.data.api.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor() {
    
    private val apiService: DeepSeekApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeepSeekApiService::class.java)
    }
    
    suspend fun generateRoadmap(goalTitle: String, goalDescription: String, apiKey: String): Result<List<String>> {
        return try {
            val prompt = """
                Create a detailed roadmap for achieving this goal:
                Title: $goalTitle
                Description: $goalDescription
                
                Please provide 5-7 specific, actionable steps in order of execution.
                Format each step as a clear, concise statement.
            """.trimIndent()
            
            val request = DeepSeekRequest(
                messages = listOf(
                    Message("user", prompt)
                )
            )
            
            val response = apiService.generateRoadmap(
                authorization = "Bearer $apiKey",
                request = request
            )
            
            if (response.isSuccessful) {
                val roadmapText = response.body()?.choices?.firstOrNull()?.message?.content
                val roadmapSteps = roadmapText?.split("\n")
                    ?.filter { it.isNotBlank() }
                    ?.map { it.trim().removePrefix("-").removePrefix("*").trim() }
                    ?: emptyList()
                
                Result.success(roadmapSteps)
            } else {
                Result.failure(Exception("API call failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun generateDailyTasks(roadmapStep: String, apiKey: String): Result<List<String>> {
        return try {
            val prompt = """
                Break down this roadmap step into 3-5 daily actionable tasks:
                Step: $roadmapStep
                
                Each task should be:
                - Specific and measurable
                - Completable in 1-2 hours
                - Progressive towards the step goal
            """.trimIndent()
            
            val request = DeepSeekRequest(
                messages = listOf(
                    Message("user", prompt)
                )
            )
            
            val response = apiService.generateRoadmap(
                authorization = "Bearer $apiKey",
                request = request
            )
            
            if (response.isSuccessful) {
                val tasksText = response.body()?.choices?.firstOrNull()?.message?.content
                val tasks = tasksText?.split("\n")
                    ?.filter { it.isNotBlank() }
                    ?.map { it.trim().removePrefix("-").removePrefix("*").trim() }
                    ?: emptyList()
                
                Result.success(tasks)
            } else {
                Result.failure(Exception("API call failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
