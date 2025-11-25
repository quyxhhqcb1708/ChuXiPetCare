package com.example.chuxipetcare.data.model

import com.google.gson.annotations.SerializedName

// Cấu trúc này dựa trên JSON của OpenWeatherMap
data class WeatherResponse(
    @SerializedName("main") val main: MainInfo,
    @SerializedName("weather") val weather: List<WeatherInfo>,
    @SerializedName("name") val cityName: String
)

data class MainInfo(
    @SerializedName("temp") val temp: Float,     // Nhiệt độ
    @SerializedName("humidity") val humidity: Int // Độ ẩm
)

data class WeatherInfo(
    @SerializedName("description") val description: String, // Ví dụ: "mưa nhẹ"
    @SerializedName("main") val mainCondition: String // Ví dụ: "Rain", "Clear"
)