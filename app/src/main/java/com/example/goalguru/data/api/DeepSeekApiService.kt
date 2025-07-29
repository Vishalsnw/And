
package com.example.goalguru.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DeepSeekApiService {
    @POST("chat/completions")
    suspend fun generateRoadmap(
        @Header("Authorization") authorization: String,
        @Body request: DeepSeekRequest,
    ): Response<DeepSeekResponse>
}

data class DeepSeekRequest(
    val model: String = "deepseek-chat",
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1000,
)

data class Message(
    val role: String,
    val content: String,
)

data class DeepSeekResponse(
    val choices: List<Choice>,
)

data class Choice(
    val message: Message,
)
package com.example.goalguru.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DeepSeekApiService {
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun generateRoadmap(@Body request: DeepSeekRequest): Response<DeepSeekResponse>
}

data class DeepSeekRequest(
    val model: String = "deepseek-chat",
    val messages: List<Message>,
    val temperature: Float = 0.7f
)

data class Message(
    val role: String,
    val content: String
)

data class DeepSeekResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
