package com.example.tp3.api

import com.example.tp3.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    fun getQuebecWeather(
        @Query("latitude") latitude: Double = 46.8139,
        @Query("longitude") longitude: Double = -71.2080,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m,surface_pressure",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "America/Toronto",
        @Query("forecast_days") forecastDays: Int = 1
    ): Call<WeatherResponse>
}