package com.example.chuxipetcare.data.repository

import com.example.chuxipetcare.data.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetRepository {
    private val db = FirebaseFirestore.getInstance()
    private val petsRef = db.collection("pets")

    // Thêm thú cưng
    suspend fun addPet(pet: Pet) {
        petsRef.add(pet).await()
    }

    // Lấy danh sách
    suspend fun getPets(): List<Pet> {
        return try {
            val snapshot = petsRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Pet::class.java)?.apply { id = doc.id }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    // Sửa thông tin thú cưng
    suspend fun updatePet(pet: Pet) {
        try {
            // Cập nhật lại document có ID tương ứng trên Firebase
            if (pet.id.isNotEmpty()) {
                petsRef.document(pet.id).set(pet).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Xóa thú cưng
    suspend fun deletePet(pet: Pet) {
        try {
            if (pet.id.isNotEmpty()) {
                petsRef.document(pet.id).delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}