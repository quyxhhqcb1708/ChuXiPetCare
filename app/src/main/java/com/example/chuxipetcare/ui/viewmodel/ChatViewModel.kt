package com.example.chuxipetcare.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chuxipetcare.data.model.ChatMessage
import com.example.chuxipetcare.data.model.Pet
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // API Key của bạn (Đã đúng, không cần đổi)
    private val apiKey = "AIzaSyDagpWUz9jULeOj6_RXhXYbkNmIGNTUC9U"

    // 👇 SỬA THÀNH CÁI NÀY (Lấy từ ảnh bạn gửi)
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    private val _messages = MutableLiveData<List<ChatMessage>>(emptyList())
    val messages: LiveData<List<ChatMessage>> get() = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun sendMessage(userText: String, contextPet: Pet?) {
        val currentList = _messages.value.orEmpty().toMutableList()
        currentList.add(ChatMessage(userText, true))
        _messages.value = currentList
        _isLoading.value = true

        val petInfo = if (contextPet != null)
            "Thông tin thú cưng: ${contextPet.species} tên ${contextPet.name}, ${contextPet.age} tuổi, ${contextPet.weight}kg. Sức khỏe: ${contextPet.healthNotes}."
        else ""

        val prompt = "Bạn là bác sĩ thú y. $petInfo Câu hỏi: $userText"

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(prompt)
                val aiText = response.text ?: "Xin lỗi, tôi không hiểu."

                currentList.add(ChatMessage(aiText, false))
                _messages.value = currentList
            } catch (e: Exception) {
                // In lỗi chi tiết
                val errorMsg = "Lỗi: ${e.message}"
                currentList.add(ChatMessage(errorMsg, false))
                _messages.value = currentList
            } finally {
                _isLoading.value = false
            }
        }
    }
}