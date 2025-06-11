package com.example.weather.ui.uiState

data class LoadingState(
    val isLoading: Boolean = true
)

data class CurrentWeatherState(
    val locationName: String = "",
    val temperature: String = "",
    val weatherDescription: String = "",
    val highTemperature: String = "",
    val lowTemperature: String = "",
    val weatherIcon: Int = 0,
    val weatherCode: Int? = null,
    val isNightMode: Boolean = false
)

data class HourlyForecastState(
    val forecastTime: List<String> = emptyList(),
    val forecastTemp: List<String> = emptyList(),
    val forecastIcon: List<String> = emptyList()
)

data class WeeklyForecastState(
    val dayName: List<String> = emptyList(),
    val dayTemp: List<String> = emptyList(),
    val dayIcon: List<String> = emptyList()
)

data class WeatherDetailsState(
    val windSpeed: String = "",
    val humidity: String = "",
    val rain: String = "",
    val uv: String = "",
    val pressure: String = "",
    val feelsLike: String = ""
)

data class WeatherUiState(
    val loading: LoadingState = LoadingState(),
    val currentWeather: CurrentWeatherState = CurrentWeatherState(),
    val hourlyForecast: HourlyForecastState = HourlyForecastState(),
    val weeklyForecast: WeeklyForecastState = WeeklyForecastState(),
    val weatherDetails: WeatherDetailsState = WeatherDetailsState()
)
data class WeeklyForecastItem(
    val day: String,
    val maxTemp: Int,
    val minTemp: Int,
    val iconResId: Int
)
data class HourlyForecastItem(
    val weatherIconRes: Int,
    val temperature: String,
    val hour: String,


)