
package com.example.goalguru.data.model

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
