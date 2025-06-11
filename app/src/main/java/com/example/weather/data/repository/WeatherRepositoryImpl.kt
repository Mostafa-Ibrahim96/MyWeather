package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.model.LocationModel
import com.example.weather.data.model.WeatherApiResponse
import com.example.weather.data.remote.ApiConfig
import com.example.weather.data.remote.LocationProvider
import com.example.weather.ui.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val locationProvider: LocationProvider
) : WeatherRepository {
    override suspend fun getCurrentWeather(location: LocationModel?): WeatherApiResponse {
        val currentLocation = locationProvider.getCurrentLocation()
        if (currentLocation != null) {
            return try {
                val url = "${ApiConfig.BASE_URL}${ApiConfig.WEATHER_ENDPOINT}" +
                        "?latitude=${currentLocation.latitude}&longitude=${currentLocation.longitude}" +
                        "&daily=temperature_2m_max,temperature_2m_min,weather_code&hourly=temperature_2m,weather_code&models=icon_seamless&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
                val response = client.get(url).body<WeatherApiResponse>()
                Log.d("WeatherRepositoryImpl", "getCurrentWeather: $response")
                response
            } catch (e: Exception) {
                throw Exception()
            }
        } else {
            throw Exception("Location not available")
        }
    }
}


