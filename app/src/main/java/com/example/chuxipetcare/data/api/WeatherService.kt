package com.example.chuxipetcare.data.api

import com.example.chuxipetcare.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,       // Tên thành phố (vd: Hanoi)
        @Query("appid") apiKey: String,     // Key API của bạn
        @Query("units") units: String = "metric", // Đơn vị độ C
        @Query("lang") lang: String = "vi"  // Trả về tiếng Việt
    ): Call<WeatherResponse>
}