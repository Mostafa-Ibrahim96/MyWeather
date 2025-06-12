package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.DataException
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
                        "&daily=${ApiConfig.WEATHER_DAILY_PARAMETER}" +
                        "&hourly=${ApiConfig.HOURLY}" +
                        "&current=${ApiConfig.WEATHER_CURRENT_PARAMETER}"

                val response = client.get(url).body<WeatherApiResponse>()
                Log.d("WeatherRepositoryImpl", "getCurrentWeather: $response")
                response
            } catch (e: Exception) {
                Log.e("WeatherRepositoryImpl", "Error: ${e.message}", e)
                throw DataException.NetworkException(e.message)
            }
        } else {
            throw DataException.LocationNotAvailableException()
        }
    }
}

