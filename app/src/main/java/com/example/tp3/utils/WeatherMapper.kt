package com.example.tp3.utils

import com.example.tp3.R
import com.example.tp3.model.WeatherResponse
import com.example.tp3.model.WeatherUiModel

object WeatherMapper {

    fun toUiModel(response: WeatherResponse): WeatherUiModel? {
        val current = response.current ?: return null
        val daily = response.daily ?: return null

        val minTemp = daily.minTemperatures?.firstOrNull()
        val maxTemp = daily.maxTemperatures?.firstOrNull()
        val code = current.weatherCode ?: -1

        return WeatherUiModel(
            city = "Québec",
            temperature = formatTemperature(current.temperature),
            description = weatherDescription(code),
            tempMin = formatTemperature(minTemp),
            tempMax = formatTemperature(maxTemp),
            humidity = current.humidity?.let { "$it %" } ?: "N/D",
            windSpeed = current.windSpeed?.let { String.format("%.1f km/h", it) } ?: "N/D",
            pressure = current.pressure?.let { String.format("%.0f hPa", it) } ?: "N/D",
            feelsLike = formatTemperature(current.apparentTemperature),
            weatherCode = code
        )
    }

    fun weatherDescription(code: Int): String {
        return when (code) {
            0 -> "Ensoleillé"
            1, 2, 3 -> "Nuageux"
            45, 48 -> "Brouillard"
            51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> "Pluie"
            71, 73, 75, 77, 85, 86 -> "Neige"
            95, 96, 99 -> "Orage"
            else -> "Conditions variables"
        }
    }

    fun weatherIconRes(code: Int): Int {
        return when (code) {
            0 -> R.drawable.ic_weather_sun
            1, 2, 3, 45, 48 -> R.drawable.ic_weather_cloud
            51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> R.drawable.ic_weather_rain
            71, 73, 75, 77, 85, 86 -> R.drawable.ic_weather_snow
            95, 96, 99 -> R.drawable.ic_weather_storm
            else -> R.drawable.ic_weather_cloud
        }
    }

    private fun formatTemperature(value: Double?): String {
        return value?.let { String.format("%.1f°C", it) } ?: "N/D"
    }
}