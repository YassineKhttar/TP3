package com.example.tp3.model

data class WeatherUiModel(
    val city: String,
    val temperature: String,
    val description: String,
    val tempMin: String,
    val tempMax: String,
    val humidity: String,
    val windSpeed: String,
    val pressure: String,
    val feelsLike: String,
    val weatherCode: Int
)