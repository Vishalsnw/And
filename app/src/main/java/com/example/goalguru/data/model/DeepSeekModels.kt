package com.example.goalguru.data.model

data class DeepSeekRequest(
    val model: String = "deepseek-chat",
    val messages: List<Message>,
    val stream: Boolean = false,
    val max_tokens: Int = 1000,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)

data class DeepSeekResponse(
    val id: String,
    val choices: List<Choice>,
    val usage: Usage? = null
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String?
)
package com.example.goalguru.data.model

import com.google.gson.annotations.SerializedName

data class DeepSeekRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double = 0.7,
    @SerializedName("max_tokens")
    val maxTokens: Int = 1000
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
