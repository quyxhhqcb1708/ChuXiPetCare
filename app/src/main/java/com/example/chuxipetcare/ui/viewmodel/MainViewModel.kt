package com.example.chuxipetcare.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.data.repository.PetRepository
// Import thư viện AI của Google
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = PetRepository()

    // LiveData chứa danh sách thú cưng để hiển thị lên màn hình
    private val _pets = MutableLiveData<List<Pet>>()
    val pets: LiveData<List<Pet>> get() = _pets

    // LiveData chứa câu trả lời từ AI
    private val _aiResponse = MutableLiveData<String>()
    val aiResponse: LiveData<String> get() = _aiResponse

    // --- CẤU HÌNH AI ---
    // Sử dụng model gemini-pro vì tính ổn định cao
    private val gemini = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyDu6K4VUAYjEpFxPWHFYiEccSp-yXKVMSU" // API Key của bạn
    )

    // --- CÁC HÀM TƯƠNG TÁC VỚI DỮ LIỆU THÚ CƯNG ---

    // 1. Tải danh sách thú cưng từ Firebase về
    fun loadPets() {
        viewModelScope.launch {
            try {
                val list = repository.getPets()
                _pets.value = list
            } catch (e: Exception) {
                // Nếu lỗi, trả về danh sách rỗng để tránh crash app
                _pets.value = emptyList()
            }
        }
    }

    // 2. Thêm thú cưng mới
    fun savePet(pet: Pet) {
        viewModelScope.launch {
            repository.addPet(pet)
            loadPets() // Tải lại danh sách ngay sau khi thêm để cập nhật màn hình
        }
    }

    // 3. Cập nhật thông tin thú cưng (Sửa) -> CẦN THÊM HÀM NÀY
    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            repository.updatePet(pet) // Gọi hàm update bên Repository
            loadPets() // Tải lại danh sách để thấy sự thay đổi
        }
    }

    // 4. Xóa thú cưng -> CẦN THÊM HÀM NÀY
    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            repository.deletePet(pet) // Gọi hàm delete bên Repository
            loadPets() // Tải lại danh sách (lúc này sẽ mất con vật vừa xóa)
        }
    }
}