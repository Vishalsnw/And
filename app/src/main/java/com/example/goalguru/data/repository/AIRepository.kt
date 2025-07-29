
package com.example.goalguru.data.repository

import com.example.goalguru.data.api.DeepSeekApiService
import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.Message
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.goalguru.data.api.DeepSeekRequest
import com.example.goalguru.data.api.Message
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
}
