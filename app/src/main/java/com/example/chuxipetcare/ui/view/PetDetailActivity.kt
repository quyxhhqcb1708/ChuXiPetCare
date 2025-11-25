package com.example.chuxipetcare.ui.view

import android.net.Uri
import android.os.Bundle
import com.example.chuxipetcare.R
import androidx.appcompat.app.AppCompatActivity
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.databinding.ActivityPetDetailBinding

// Trong PetDetailActivity.kt

// ... các import khác
import com.bumptech.glide.Glide
import android.util.Base64 // <--- THÊM DÒNG NÀY

class PetDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pet = intent.getSerializableExtra("pet_data") as? Pet

        pet?.let {
            binding.tvPetDetailName.text = it.name
            binding.tvPetDetailSpecies.text = "Giống loài: ${it.species}"
            binding.tvPetDetailAge.text = "Tuổi: ${it.age}"
            binding.tvPetDetailWeight.text = "Cân nặng: ${it.weight} kg"
            binding.tvPetDetailHealthNotes.text = it.healthNotes

            // HIỂN THỊ CÁC THÔNG TIN MỚI
            binding.tvPetDetailLastVaccine.text = "Tiêm vaccine: ${it.lastVaccineDate.ifEmpty { "Chưa có" }}"
            binding.tvPetDetailLastDeworming.text = "Tẩy giun: ${it.lastDewormingDate.ifEmpty { "Chưa có" }}"

            // Sử dụng Glide để hiển thị ảnh
            // ... trong phần pet?.let { ...
            if (it.imageUri.isNotEmpty()) {
                try {
                    // Giải mã chuỗi Base64 thành mảng byte để Glide đọc
                    val imageBytes = Base64.decode(it.imageUri, Base64.DEFAULT)

                    Glide.with(this)
                        .load(imageBytes) // Glide load được trực tiếp từ byte array
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.ivPetDetailImage)
                } catch (e: Exception) {
                    binding.ivPetDetailImage.setImageResource(R.drawable.ic_launcher_background)
                }
            } else {
                binding.ivPetDetailImage.setImageResource(R.drawable.ic_launcher_background)
            }
// ...
        }
    }
}
