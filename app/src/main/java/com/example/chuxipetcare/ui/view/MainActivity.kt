package com.example.chuxipetcare.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chuxipetcare.data.model.Pet
import com.example.chuxipetcare.databinding.ActivityMainBinding
import com.example.chuxipetcare.ui.adapter.PetAdapter
import com.example.chuxipetcare.ui.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.chuxipetcare.data.api.WeatherService
import com.example.chuxipetcare.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ------------------ PHẦN THỜI TIẾT ------------------
        // 1. Khởi tạo Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        // 2. Gọi API lấy thời tiết Hà Nội
        // API KEY DÙNG TẠM: "91b7466cc755db1a94cdf525abedb3e3"
        weatherService.getCurrentWeather("Hanoi", "cbe4d33220f39ca4d87931971df1cd26").enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    weatherData?.let {
                        val temp = it.main.temp

                        // Gọi hàm đưa ra lời khuyên (Hàm này giờ đã nằm ở dưới class, không phải trong onCreate nữa)
                        giveAdvice(temp, it.weather[0].mainCondition)
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                binding.tvWeather.text = "Lỗi kết nối thời tiết!"
            }
        })
        // ----------------------------------------------------

        // Adapter setup
        val adapter = PetAdapter(
            onClick = { pet ->
                // Sửa lại dùng TempStorage nếu cần, hoặc dùng Intent như cũ nếu đã fix lỗi crash
                val intent = Intent(this, PetDetailActivity::class.java)
                intent.putExtra("pet_data", pet)
                startActivity(intent)
            },
            onMenuClick = { view, pet ->
                showPopupMenu(view, pet)
            },
            onChatClick = { pet ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("pet_data", pet)
                startActivity(intent)
            }
        )

        binding.rvPets.layoutManager = LinearLayoutManager(this)
        binding.rvPets.adapter = adapter

        viewModel.pets.observe(this) { list ->
            adapter.updateList(list)
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddPetActivity::class.java))
        }
    }

    // --- CÁC HÀM CON ĐƯA RA NGOÀI ONCREATE ---

    // Hàm xử lý lời khuyên thời tiết
    private fun giveAdvice(temp: Float, condition: String) {
        var advice = ""
        var icon = ""

        if (condition.contains("Rain") || condition.contains("Drizzle")) {
            advice = "Trời mưa, hạn chế cho Boss ra ngoài nhé!"
            icon = "🌧️"
        } else if (temp < 18) {
            advice = "Trời lạnh, hãy giữ ấm cho Boss."
            icon = "❄️"
        } else if (temp > 32) {
            advice = "Trời nóng, cho Boss uống nhiều nước!"
            icon = "☀️"
        } else {
            advice = "Thời tiết đẹp, dắt Boss đi dạo thôi!"
            icon = "🌤️"
        }

        // Cập nhật lên giao diện (TextView tvWeather đã thêm ở file xml)
        binding.tvWeather.text = "$icon Hà Nội ${temp.toInt()}°C: $advice"
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPets()
    }

    private fun showPopupMenu(view: View, pet: Pet) {
        val popup = PopupMenu(this, view)
        popup.menu.add(0, 1, 0, "Sửa")
        popup.menu.add(0, 2, 0, "Xóa")

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> { // Chọn Sửa
                    val intent = Intent(this, AddPetActivity::class.java)
                    intent.putExtra("pet_edit", pet)
                    startActivity(intent)
                    true
                }
                2 -> { // Chọn Xóa
                    viewModel.deletePet(pet)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}