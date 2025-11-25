package com.example.chuxipetcare.ui.adapter

import android.util.Base64 // <--- QUAN TRỌNG: Phải là android.util, không phải java.util
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chuxipetcare.R
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.databinding.ItemPetBinding

class PetAdapter(
    private val onClick: (Pet) -> Unit,
    private val onMenuClick: (View, Pet) -> Unit,
    private val onChatClick: (Pet) -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    private var list = listOf<Pet>()

    fun updateList(newList: List<Pet>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class PetViewHolder(val binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = list[position]

        // Dùng with() để code gọn hơn, đỡ phải gõ holder.binding nhiều lần
        with(holder.binding) {
            // 1. Hiển thị thông tin text
            tvName.text = pet.name.ifEmpty { "Không tên" }
            val type = if (pet.species.isNotEmpty()) pet.species else "Thú cưng"
            tvInfo.text = "$type • ${pet.age} tuổi • ${pet.weight}kg"

            // 2. Load ảnh (Đã sửa lỗi context và binding)
            if (pet.imageUri.isNotEmpty()) {
                try {
                    val imageBytes = Base64.decode(pet.imageUri, Base64.DEFAULT)
                    Glide.with(root.context) // <--- SỬA: Dùng root.context thay vì binding.root.context
                        .load(imageBytes)
                        .circleCrop()
                        .into(ivPetAvatar)   // <--- SỬA: Dùng ivPetAvatar trực tiếp
                } catch (e: Exception) {
                    ivPetAvatar.setImageResource(R.drawable.ic_launcher_background)
                }
            } else {
                ivPetAvatar.setImageResource(R.drawable.ic_launcher_background)
            }

            // 3. Xử lý sự kiện CLICK
            root.setOnClickListener { onClick(pet) }

            // Nút Chat
            // Lưu ý: Đảm bảo trong item_pet.xml có id là btnChat
            // Nếu báo đỏ btnChat thì kiểm tra lại file XML xem đặt id đúng chưa
            btnChat.setOnClickListener { onChatClick(pet) }

            // Nút Menu
            btnMenu.setOnClickListener { view -> onMenuClick(view, pet) }
        }
    }

    override fun getItemCount() = list.size
}