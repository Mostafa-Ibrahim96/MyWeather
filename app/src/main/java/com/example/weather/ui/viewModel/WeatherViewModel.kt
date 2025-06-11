package com.example.weather.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.R
import com.example.weather.data.remote.LocationProvider
import com.example.weather.ui.repository.WeatherRepository
import com.example.weather.ui.uiState.HourlyForecastItem
import com.example.weather.ui.uiState.WeatherDetailUiState
import com.example.weather.ui.uiState.WeatherDetailsGrid
import com.example.weather.ui.uiState.WeatherUiState
import com.example.weather.ui.uiState.WeeklyForecastItem
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
    private val _state = MutableStateFlow(WeatherUiState())
    val state = _state.asStateFlow()

    private val _weatherDetails = MutableStateFlow(WeatherDetailsGrid())
    val weatherDetails = _weatherDetails.asStateFlow()

    private val _hourlyForecast = MutableStateFlow<List<HourlyForecastItem>>(emptyList())
    val hourlyForecast = _hourlyForecast.asStateFlow()

    private val _weeklyForecast = MutableStateFlow<List<WeeklyForecastItem>>(emptyList())

    val weeklyForecast = _weeklyForecast.asStateFlow()

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            val weatherData = weatherRepository.getCurrentWeather(location)
            val firstHourTime = weatherData?.current?.time
            val currentHour = firstHourTime?.split("T")?.get(1)?.substring(0, 2)?.toIntOrNull()
                ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val currentMinute = firstHourTime?.split("T")?.get(1)?.substring(3, 5)?.toIntOrNull()
                ?: Calendar.getInstance().get(Calendar.MINUTE)
            val isNight = weatherData?.current?.is_day == 0

            if (weatherData != null && weatherData.current != null) {
                val weatherCode = weatherData.current.weather_code ?: 0
                _state.value = _state.value.copy(
                    loading = _state.value.loading.copy(isLoading = false),
                    currentWeather = _state.value.currentWeather.copy(
                        locationName = location?.country ?: "Unknown",
                        temperature = weatherData.current.temperature_2m?.toString() ?: "N/A",
                        weatherDescription = mapWeatherCodeToDescription(weatherCode),
                        highTemperature = weatherData.daily?.temperature_2m_max?.firstOrNull()
                            ?.toString() ?: "N/A",
                        lowTemperature = weatherData.daily?.temperature_2m_min?.firstOrNull()
                            ?.toString() ?: "N/A",
                        weatherIcon = mapWeatherCodeToResource(weatherCode, isNight),
                        weatherCode = weatherCode,
                        isNightMode = isNight
                    )
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

                    val forecastList = hourly.time?.mapIndexed { index, time ->
                        val hourTime = time.split("T")[1]  // "HH:MM"
                        val temp = hourly.temperature_2m?.getOrNull(index)?.toInt() ?: 0
                        val weatherCode = hourly.weather_code?.getOrNull(index) ?: 0
                        val forecastHour = hourTime.substring(0, 2).toIntOrNull() ?: 0
                        val isNightForHour = forecastHour >= 18 || forecastHour < 6
                        HourlyForecastItem(
                            hour = hourTime,
                            temperature = "$temp°C",
                            weatherIconRes = mapWeatherCodeToResource(weatherCode, isNightForHour)
                        )
                    } ?: emptyList()

                    val filteredForecast = forecastList.filter { item ->
                        val forecastHour = item.hour.substring(0, 2).toIntOrNull() ?: 0
                        val forecastMinute = item.hour.substring(3, 5).toIntOrNull() ?: 0
                        (forecastHour > currentHour) || (forecastHour == currentHour && forecastMinute >= currentMinute)
                    }.map { it.copy() }
                        .take(24 - currentHour - 1)


                    _hourlyForecast.value = filteredForecast


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
                        val weatherCode = daily.weather_code?.getOrNull(index) ?: 0
                        WeeklyForecastItem(
                            day,
                            maxTemp,
                            minTemp,
                            mapWeatherCodeToResource(weatherCode, false)
                        )
                    }?.take(7) ?: emptyList()
                    _weeklyForecast.value = forecastList
                }
            } else {
                _state.value = _state.value.copy(
                    loading = _state.value.loading.copy(isLoading = false),
                    currentWeather = _state.value.currentWeather.copy(weatherDescription = "Failed to load weather data")
                )
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


    private fun mapWeatherCodeToResource(weatherCode: Int?, isNight: Boolean): Int {
        return when (weatherCode) {
            0 -> if (isNight) R.drawable.clear_sky else R.drawable.clear_sky
            1 -> if (isNight) R.drawable.mainlyclear else R.drawable.mainlyclear
            2 -> if (isNight) R.drawable.partly_cloudy else R.drawable.partly_cloudy
            3 -> if (isNight) R.drawable.overcast else R.drawable.overcast
            45 -> if (isNight) R.drawable.fog else R.drawable.fog
            48 -> if (isNight) R.drawable.depositing_rime_fog else R.drawable.depositing_rime_fog
            51 -> if (isNight) R.drawable.drizzle_light else R.drawable.drizzle_light
            53 -> if (isNight) R.drawable.drizzle_moderate else R.drawable.drizzle_moderate
            55 -> if (isNight) R.drawable.drizzle_intensity else R.drawable.drizzle_intensity
            56 -> if (isNight) R.drawable.freezing_drizzle_light else R.drawable.freezing_drizzle_light
            57 -> if (isNight) R.drawable.freezing_drizzle_intensity else R.drawable.freezing_drizzle_intensity
            61 -> if (isNight) R.drawable.rain_slight else R.drawable.rain_slight
            63 -> if (isNight) R.drawable.rain_moderate else R.drawable.rain_moderate
            65 -> if (isNight) R.drawable.rain_intensity else R.drawable.rain_intensity
            66 -> if (isNight) R.drawable.freezing_loght else R.drawable.freezing_loght
            67 -> if (isNight) R.drawable.freezing_heavy else R.drawable.freezing_heavy
            71 -> if (isNight) R.drawable.snow_fall_light else R.drawable.snow_fall_light
            73 -> if (isNight) R.drawable.snow_fall_moderate else R.drawable.snow_fall_moderate
            75 -> if (isNight) R.drawable.snow_fall_intensity else R.drawable.snow_fall_intensity
            77 -> if (isNight) R.drawable.snow_grains else R.drawable.snow_grains
            80 -> if (isNight) R.drawable.rain_shower_slight else R.drawable.rain_shower_slight
            81 -> if (isNight) R.drawable.rain_shower_moderate else R.drawable.rain_shower_moderate
            82 -> if (isNight) R.drawable.rain_shower_violent else R.drawable.rain_shower_violent
            85 -> if (isNight) R.drawable.snow_shower_slight else R.drawable.snow_shower_slight
            86 -> if (isNight) R.drawable.snow_shower_heavy else R.drawable.snow_shower_heavy
            95 -> if (isNight) R.drawable.thunderstrom_slight_or_moderate else R.drawable.thunderstrom_slight_or_moderate
            96 -> if (isNight) R.drawable.thunderstrom_with_slight_hail else R.drawable.thunderstrom_with_slight_hail
            99 -> if (isNight) R.drawable.thunderstrom_with_heavy_hail else R.drawable.thunderstrom_with_heavy_hail
            else -> if (isNight) R.drawable.fastwind else R.drawable.fastwind
        }
    }


}