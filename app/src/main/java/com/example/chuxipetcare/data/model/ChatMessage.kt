package com.example.chuxipetcare.data.model

data class ChatMessage(
    val text: String,
    val isUser: Boolean // true: Người hỏi, false: AI trả lời
)