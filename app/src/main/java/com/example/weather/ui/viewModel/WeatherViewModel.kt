package com.example.weather.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.R
import com.example.weather.data.remote.LocationProvider
import com.example.weather.ui.repository.WeatherRepository
import com.example.weather.ui.uiState.WeatherDetailUiState
import com.example.weather.ui.uiState.WeatherDetailsGrid
import com.example.weather.ui.uiState.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider = getKoin().get()
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherUiState(isLoading = true))
    val state = _state.asStateFlow()

    private val _weatherDetails = MutableStateFlow(WeatherDetailsGrid())
    val weatherDetails = _weatherDetails.asStateFlow()

    private val _hourlyForecast = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val hourlyForecast = _hourlyForecast.asStateFlow()

    private val _weeklyForecast =
        MutableStateFlow<List<Triple<String, Int, Int>>>(emptyList()) // Day, Max Temp, Min Temp
    val weeklyForecast = _weeklyForecast.asStateFlow()

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            val weatherData = weatherRepository.getCurrentWeather(location)
            if (weatherData != null && weatherData.current != null) {
                val weatherCode = weatherData.current.weather_code ?: 0
                _state.value = _state.value.copy(
                    isLoading = false,
                    locationName = location!!.country ?: "Unknown",
                    temperature = weatherData.current.temperature_2m?.toString() ?: "N/A",
                    weatherDescription = mapWeatherCodeToDescription(weatherCode),
                    highTemperature = weatherData.daily?.temperature_2m_max?.firstOrNull()
                        ?.toString() ?: "N/A",
                    lowTemperature = weatherData.daily?.temperature_2m_min?.firstOrNull()
                        ?.toString() ?: "N/A",
                    weatherIcon = mapWeatherCodeToResource(weatherCode)
                )

                _weatherDetails.value = WeatherDetailsGrid(
                    detailGrid = listOf(
                        WeatherDetailUiState(
                            windSpeed = weatherData.current.wind_speed_10m?.toString() ?: "N/A",
                            weatherCode = weatherCode
                        ),
                        WeatherDetailUiState(
                            humidity = weatherData.current.relative_humidity_2m?.toString()
                                ?: "N/A", weatherCode = weatherCode
                        ),
                        WeatherDetailUiState(
                            rain = weatherData.current.rain?.toString() ?: "N/A",
                            weatherCode = weatherCode
                        ),
                        WeatherDetailUiState(uv = "2", weatherCode = weatherCode),
                        WeatherDetailUiState(
                            pressure = weatherData.current.pressure_msl?.toString() ?: "N/A",
                            weatherCode = weatherCode
                        ),
                        WeatherDetailUiState(
                            feelsLike = weatherData.current.apparent_temperature?.toString()
                                ?: "N/A", weatherCode = weatherCode
                        )
                    )
                )


                weatherData.hourly?.let { hourly ->
                    val forecastList = hourly.time?.zip(hourly.temperature_2m ?: emptyList())
                        ?.map { (time, temp) ->
                            Pair(time.split("T")[1], "${temp.toInt()}°C")
                        }?.take(24) ?: emptyList()
                    _hourlyForecast.value = forecastList
                }

                weatherData.daily?.let { daily ->
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val calendar = Calendar.getInstance()
                    val forecastList = daily.time?.mapIndexed { index, date ->
                        calendar.time = sdf.parse(date)!!
                        val day = calendar.getDisplayName(
                            Calendar.DAY_OF_WEEK,
                            Calendar.LONG,
                            Locale.getDefault()
                        ) ?: "Day"
                        val maxTemp = daily.temperature_2m_max?.getOrNull(index)?.toInt() ?: 0
                        val minTemp = daily.temperature_2m_min?.getOrNull(index)?.toInt() ?: 0
                        Triple(day, maxTemp, minTemp)
                    }?.take(7) ?: emptyList()
                    _weeklyForecast.value = forecastList
                }
            } else {
                _state.value = _state.value.copy(weatherDescription = "Failed to load weather data")
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun mapWeatherCodeToDescription(weatherCode: Int?): String {
        return when (weatherCode) {
            0 -> "Clear sky"
            1, 2, 3 -> "Partly cloudy"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rain"
            71, 73, 75 -> "Snow"
            95 -> "Thunderstorm"
            else -> "Unknown"
        }
    }


    private fun mapWeatherCodeToResource(weatherCode: Int?): Int {
        return when (weatherCode) {
            0 -> R.drawable.clear_sky
            1 -> R.drawable.mainlyclear
            2 -> R.drawable.partly_cloudy
            3 -> R.drawable.overcast
            45 -> R.drawable.fog
            48 -> R.drawable.depositing_rime_fog
            51 -> R.drawable.drizzle_light
            53 -> R.drawable.drizzle_moderate
            55 -> R.drawable.drizzle_intensity
            56 -> R.drawable.freezing_drizzle_light
            57 -> R.drawable.freezing_drizzle_intensity
            61 -> R.drawable.rain_slight
            63 -> R.drawable.rain_moderate
            65 -> R.drawable.rain_intensity
            66 -> R.drawable.freezing_loght
            67 -> R.drawable.freezing_heavy
            71 -> R.drawable.snow_fall_light
            73 -> R.drawable.snow_fall_moderate
            75 -> R.drawable.snow_fall_intensity
            77 -> R.drawable.snow_grains
            80 -> R.drawable.rain_shower_slight
            81 -> R.drawable.rain_shower_moderate
            82 -> R.drawable.rain_shower_violent
            85 -> R.drawable.snow_shower_slight
            86 -> R.drawable.snow_shower_heavy
            95 -> R.drawable.thunderstrom_slight_or_moderate
            96 -> R.drawable.thunderstrom_with_slight_hail
            99 -> R.drawable.thunderstrom_with_heavy_hail
            else -> R.drawable.fastwind
        }
    }


}