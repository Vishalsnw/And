package com.example.goalguru.data.api

import com.example.goalguru.data.model.DeepSeekRequest
import com.example.goalguru.data.model.DeepSeekResponse
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

    @POST("chat/completions")
    suspend fun generateCompletion(
        @Body request: DeepSeekRequest,
    ): DeepSeekResponse
}
