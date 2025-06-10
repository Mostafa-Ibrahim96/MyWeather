package com.example.weather.ui.uiState


data class WeatherDetailsGrid(
    val detailGrid: List<WeatherDetailUiState> = emptyList()
)

data class WeatherDetailUiState(
    val windSpeed: String = "",
    val humidity: String = "",
    val rain: String = "",
    val uv: String = "",
    val pressure: String = "",
    val feelsLike: String = "",
    val weatherCode: Int? = null
)