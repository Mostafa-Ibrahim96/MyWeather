package com.example.weather.ui.uiState

data class WeatherUiState(
    val isLoading: Boolean = true,
    val locationName: String = "",
    val temperature: String = "",
    val weatherDescription: String = "",
    val highTemperature: String = "",
    val lowTemperature: String = "",
    val windSpeed: String = "",
    val humidity: String = "",
    val rain: String = "",
    val uv: String = "",
    val pressure: String = "",
    val feelsLike: String = "",
    val weatherCode: Int? = null ,

    val forecastTime: List<String> = emptyList(),
    val forecastTemp: List<String> = emptyList(),
    val dayName: List<String> = emptyList(),
    val dayTemp: List<String> = emptyList(),
    val weatherIcon: Int = 0,
    val forecastIcon: List<String> = emptyList(),
    val dayIcon: List<String> = emptyList()

)
