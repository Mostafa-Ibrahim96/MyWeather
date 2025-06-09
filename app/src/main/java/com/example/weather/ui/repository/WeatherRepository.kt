package com.example.weather.ui.repository


import com.example.weather.data.model.LocationModel
import com.example.weather.data.model.WeatherApiResponse


interface WeatherRepository {
    suspend fun getCurrentWeather(location: LocationModel?): WeatherApiResponse?
}