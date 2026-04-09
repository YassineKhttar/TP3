package com.example.tp3.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.api.RetrofitClient
import com.example.tp3.databinding.ActivityWeatherBinding
import com.example.tp3.model.WeatherResponse
import com.example.tp3.model.WeatherUiModel
import com.example.tp3.utils.WeatherMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private var currentWeather: WeatherUiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDetails.setOnClickListener {
            currentWeather?.let { weather ->
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_CITY, weather.city)
                    putExtra(DetailActivity.EXTRA_TEMP_MIN, weather.tempMin)
                    putExtra(DetailActivity.EXTRA_TEMP_MAX, weather.tempMax)
                    putExtra(DetailActivity.EXTRA_HUMIDITY, weather.humidity)
                    putExtra(DetailActivity.EXTRA_WIND, weather.windSpeed)
                    putExtra(DetailActivity.EXTRA_PRESSURE, weather.pressure)
                    putExtra(DetailActivity.EXTRA_FEELS_LIKE, weather.feelsLike)
                }
                startActivity(intent)
            }
        }

        binding.btnRetry.setOnClickListener {
            loadWeather()
        }

        loadWeather()
    }

    private fun loadWeather() {
        showLoading()

        RetrofitClient.apiService.getQuebecWeather().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    val uiModel = body?.let { WeatherMapper.toUiModel(it) }
                    if (uiModel == null) {
                        showNoData()
                    } else {
                        currentWeather = uiModel
                        showData(uiModel)
                    }
                } else {
                    showError("Erreur API : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                showError(t.message ?: "Erreur de connexion")
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvStatus.visibility = View.VISIBLE
        binding.tvStatus.text = "Chargement des données météo..."
        binding.groupWeather.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.tvStatus.visibility = View.GONE
        binding.groupWeather.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE
        binding.tvError.text = message
    }

    private fun showNoData() {
        binding.progressBar.visibility = View.GONE
        binding.tvStatus.visibility = View.GONE
        binding.groupWeather.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE
        binding.tvError.text = "Données indisponibles pour le moment."
    }

    private fun showData(weather: WeatherUiModel) {
        binding.progressBar.visibility = View.GONE
        binding.tvStatus.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
        binding.groupWeather.visibility = View.VISIBLE

        binding.tvCity.text = weather.city
        binding.tvTemperature.text = weather.temperature
        binding.tvDescription.text = weather.description
        binding.ivWeather.setImageResource(WeatherMapper.weatherIconRes(weather.weatherCode))
    }
}