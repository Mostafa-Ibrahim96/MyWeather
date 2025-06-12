package com.example.weather.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.remote.LocationProvider
import com.example.weather.ui.repository.WeatherRepository
import com.example.weather.ui.uiState.HourlyForecastUiState
import com.example.weather.ui.uiState.WeatherDetailsState
import com.example.weather.ui.uiState.WeatherUiState
import com.example.weather.ui.uiState.WeeklyForecastItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import java.util.Calendar

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider = getKoin().get()
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherUiState())
    val state = _state.asStateFlow()

    private val _weatherDetails = MutableStateFlow<List<WeatherDetailsState>>(emptyList())
    val weatherDetails = _weatherDetails.asStateFlow()

    private val _hourlyForecast = MutableStateFlow<List<HourlyForecastUiState>>(emptyList())
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

            _state.value = _state.value.copy(
                loading = _state.value.loading.copy(isLoading = false),
                isNight = isNight,
                currentWeather = WeatherUiMapper.mapCurrentWeather(
                    weatherData!!,
                    location?.country ?: "Unknown",
                    isNight
                )
            )

            _weatherDetails.value = WeatherUiMapper.mapWeatherDetails(weatherData)
            _hourlyForecast.value =
                WeatherUiMapper.mapHourlyForecast(weatherData.hourly, currentHour, currentMinute, isNight)
            _weeklyForecast.value = WeatherUiMapper.mapWeeklyForecast(weatherData.daily, isNight)
        }
    }
}