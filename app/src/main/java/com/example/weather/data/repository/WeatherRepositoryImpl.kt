package org.example.data.repository

import com.example.weather.data.model.LocationModel
import com.example.weather.data.model.WeatherApiResponse
import com.example.myweather.data.remote.LocationProvider
import com.example.weather.data.remote.ApiConfig
import com.example.weather.ui.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val locationProvider: LocationProvider
) : WeatherRepository {


    override suspend fun getCurrentWeather(location: LocationModel?): WeatherApiResponse? {
        val currentLocation = locationProvider.getCurrentLocation() ?: return null
        return try {
            val url = "${ApiConfig.BASE_URL}${ApiConfig.WEATHER_ENDPOINT}" +
                    "?latitude=${currentLocation.latitude}&longitude=${currentLocation.longitude}" +
                    "&daily=temperature_2m_max,temperature_2m_min,weather_code" +
                    "&hourly=temperature_2m,weather_code" +
                    "&current_weather=true" + // Corrected from ¤t
                    "&temperature_unit=celsius" +
                    "&windspeed_unit=kmh" +
                    "&precipitation_unit=mm" +
                    "&timeformat=iso8601"
            val response = client.get(url).body<WeatherApiResponse>()
            println("Fetched weather data: $response") // Log success
            response
        } catch (e: Exception) {
            println("Failed to fetch weather data: ${e.message}") // Log failure
            null
        }
    }
}


