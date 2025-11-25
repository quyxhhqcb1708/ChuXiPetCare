package com.example.chuxipetcare.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.databinding.ActivityChatBinding
import com.example.chuxipetcare.ui.adapter.ChatAdapter
import com.example.chuxipetcare.ui.viewmodel.ChatViewModel

class ChatActivity : AppCompatActivity() {
    // Khởi tạo ViewBinding cho file activity_chat.xml
    private lateinit var binding: ActivityChatBinding

    // Khởi tạo ViewModel để xử lý logic chat (gọi AI Gemini)
    private val viewModel: ChatViewModel by viewModels()

    // Adapter để hiển thị danh sách tin nhắn
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận dữ liệu thú cưng được truyền từ màn hình chính
        // (Để AI biết đang tư vấn cho con vật nào)
        val pet = intent.getSerializableExtra("pet_data") as? Pet

        // Cập nhật tiêu đề thanh công cụ
        if (pet != null) {
            supportActionBar?.title = "Tư vấn về bé ${pet.name}"
        } else {
            supportActionBar?.title = "Bác sĩ thú y AI"
        }

        // 2. Cài đặt danh sách tin nhắn (RecyclerView)
        setupRecyclerView()

        // 3. Lắng nghe dữ liệu tin nhắn từ ViewModel
        observeMessages()

        // 4. Xử lý sự kiện bấm nút Gửi
        binding.btnSend.setOnClickListener {
            sendMessage(pet)
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter()
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = adapter
    }

    private fun observeMessages() {
        viewModel.messages.observe(this) { msgs ->
            // Cập nhật danh sách tin nhắn mới vào Adapter
            adapter.submitList(msgs)

            // Tự động cuộn xuống tin nhắn cuối cùng nếu có tin nhắn
            if (msgs.isNotEmpty()) {
                binding.rvChat.scrollToPosition(msgs.size - 1)
            }
        }

        // (Tùy chọn) Bạn có thể lắng nghe thêm trạng thái isLoading để hiện vòng xoay khi AI đang nghĩ
        // viewModel.isLoading.observe(this) { isLoading -> ... }
    }

    private fun sendMessage(pet: Pet?) {
        val text = binding.etMessage.text.toString().trim()
        if (text.isNotEmpty()) {
            // Gửi tin nhắn và thông tin thú cưng sang ViewModel để xử lý
            viewModel.sendMessage(text, pet)

            // Xóa trắng ô nhập liệu
            binding.etMessage.text.clear()
        }
    }
}