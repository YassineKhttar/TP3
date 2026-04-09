package com.example.tp3.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCity.text = intent.getStringExtra(EXTRA_CITY) ?: "Québec"
        binding.tvTempMin.text = intent.getStringExtra(EXTRA_TEMP_MIN) ?: "N/D"
        binding.tvTempMax.text = intent.getStringExtra(EXTRA_TEMP_MAX) ?: "N/D"
        binding.tvHumidity.text = intent.getStringExtra(EXTRA_HUMIDITY) ?: "N/D"
        binding.tvWind.text = intent.getStringExtra(EXTRA_WIND) ?: "N/D"
        binding.tvPressure.text = intent.getStringExtra(EXTRA_PRESSURE) ?: "N/D"
        binding.tvFeelsLike.text = intent.getStringExtra(EXTRA_FEELS_LIKE) ?: "N/D"

        binding.btnRetour.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_CITY = "extra_city"
        const val EXTRA_TEMP_MIN = "extra_temp_min"
        const val EXTRA_TEMP_MAX = "extra_temp_max"
        const val EXTRA_HUMIDITY = "extra_humidity"
        const val EXTRA_WIND = "extra_wind"
        const val EXTRA_PRESSURE = "extra_pressure"
        const val EXTRA_FEELS_LIKE = "extra_feels_like"
    }
}