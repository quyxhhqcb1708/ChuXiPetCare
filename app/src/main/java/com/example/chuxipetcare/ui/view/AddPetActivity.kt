package com.example.chuxipetcare.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64 // Import cái này của Android
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.databinding.ActivityAddPetBinding
import com.example.chuxipetcare.ui.viewmodel.MainViewModel
import java.io.ByteArrayOutputStream

class AddPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetBinding
    private val viewModel: MainViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var petToEdit: Pet? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            Glide.with(this).load(uri).circleCrop().into(binding.ivPetAvatar)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        petToEdit = intent.getSerializableExtra("pet_edit") as? Pet
        setupUI()
    }

    private fun setupUI() {
        if (petToEdit != null) {
            binding.tvTitle.text = "Cập Nhật Hồ Sơ"
            binding.etName.setText(petToEdit!!.name)
            binding.etSpecies.setText(petToEdit!!.species)
            binding.etWeight.setText(petToEdit!!.weight.toString())
            binding.etAge.setText(petToEdit!!.age.toString())
            binding.etNote.setText(petToEdit!!.healthNotes)
            binding.etLastVaccine.setText(petToEdit!!.lastVaccineDate)
            binding.etLastDeworming.setText(petToEdit!!.lastDewormingDate)
            binding.btnSave.text = "Cập Nhật"

            // Load ảnh cũ
            if (petToEdit!!.imageUri.isNotEmpty()) {
                val imageBytes = Base64.decode(petToEdit!!.imageUri, Base64.DEFAULT)
                Glide.with(this).load(imageBytes).circleCrop().into(binding.ivPetAvatar)
            }
        }

        binding.ivPetAvatar.setOnClickListener { pickImageLauncher.launch("image/*") }

        binding.btnSave.setOnClickListener {
            savePetWithBase64() // Gọi hàm xử lý kiểu mới
        }
    }

    private fun savePetWithBase64() {
        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()) {
            binding.etName.error = "Nhập tên bé đi bạn ơi"
            return
        }

        // Xử lý ảnh:
        // 1. Nếu có chọn ảnh mới -> Nén -> Chuyển thành String Base64
        // 2. Nếu không chọn -> Dùng lại String cũ
        val finalImageString = if (selectedImageUri != null) {
            encodeUriToBase64(selectedImageUri!!)
        } else {
            petToEdit?.imageUri ?: ""
        }

        // Code lưu vào Firestore (giống hệt cũ)
        val pet = Pet(
            id = petToEdit?.id ?: "", // Giữ ID nếu đang sửa
            name = name,
            species = binding.etSpecies.text.toString(),
            age = binding.etAge.text.toString().toIntOrNull() ?: 0,
            weight = binding.etWeight.text.toString().toDoubleOrNull() ?: 0.0,
            healthNotes = binding.etNote.text.toString(),
            lastVaccineDate = binding.etLastVaccine.text.toString(),
            lastDewormingDate = binding.etLastDeworming.text.toString(),
            imageUri = finalImageString // Lưu chuỗi Base64 dài ngoằng vào đây
        )

        if (petToEdit == null) {
            viewModel.savePet(pet)
            Toast.makeText(this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.updatePet(pet)
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    // --- HÀM QUAN TRỌNG: Chuyển URI ảnh thành chuỗi Base64 (đã nén nhỏ) ---
    private fun encodeUriToBase64(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // 1. Resize ảnh nhỏ lại (khoảng 500px) để không bị quá nặng
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true)

            // 2. Nén thành ByteArray
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream) // Chất lượng 60%
            val byteArray = outputStream.toByteArray()

            // 3. Trả về chuỗi Base64
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}