package com.example.chuxipetcare.data.model

import java.io.Serializable

data class Pet(
    var id: String = "",
    val name: String = "",
    val species: String = "",
    val age: Int = 0,
    val weight: Double = 0.0,
    val healthNotes: String = "",
    val lastVaccineDate: String = "",
    val lastDewormingDate: String = "",
    val imageUri: String = "" // <-- THÊM TRƯỜNG NÀY
) : Serializable