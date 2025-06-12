package com.example.weather.ui.viewModel

import com.example.weather.R
import com.example.weather.data.model.WeatherApiResponse
import com.example.weather.ui.uiState.CurrentWeatherState
import com.example.weather.ui.uiState.HourlyForecastUiState
import com.example.weather.ui.uiState.WeatherDetailsState
import com.example.weather.ui.uiState.WeeklyForecastItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object WeatherUiMapper {

    fun mapCurrentWeather(
        weatherData: WeatherApiResponse,
        locationName: String,
        isNight: Boolean
    ): CurrentWeatherState {
        val current = weatherData.current ?: return CurrentWeatherState()
        val weatherCode = current.weather_code ?: 0

        return CurrentWeatherState(
            locationName = locationName,
            temperature = current.temperature_2m?.toString() ?: "N/A",
            weatherDescription = mapWeatherCodeToDescription(weatherCode),
            highTemperature = weatherData.daily?.temperature_2m_max?.firstOrNull()?.toString() ?: "N/A",
            lowTemperature = weatherData.daily?.temperature_2m_min?.firstOrNull()?.toString() ?: "N/A",
            weatherIcon = mapWeatherCodeToResource(weatherCode, isNight),
            weatherCode = weatherCode,
            isNightMode = isNight
        )
    }

    fun mapWeatherDetails(current: WeatherApiResponse): List<WeatherDetailsState> {
        return listOf(
            WeatherDetailsState(windSpeed = current.current!!.wind_speed_10m?.toString() ?: "N/A"),
            WeatherDetailsState(humidity = current.current.relative_humidity_2m?.toString() ?: "N/A"),
            WeatherDetailsState(rain = current.current.rain?.toString() ?: "N/A"),
            WeatherDetailsState(uv = current.daily!!.uv_index_max?.toString() ?:"2"),
            WeatherDetailsState(pressure = current.current.pressure_msl?.toString() ?: "N/A"),
            WeatherDetailsState(feelsLike = current.current.apparent_temperature?.toString() ?: "N/A")
        )
    }

    fun mapHourlyForecast(
        hourly: WeatherApiResponse.Hourly?,
        currentHour: Int,
        currentMinute: Int,
        isNight: Boolean
    ): List<HourlyForecastUiState> {
        if (hourly == null || hourly.time == null) return emptyList()

        val fullList = hourly.time.mapIndexedNotNull { index, time ->
            val hourTime = time.split("T").getOrNull(1) ?: return@mapIndexedNotNull null
            val temp = hourly.temperature_2m?.getOrNull(index)?.toInt() ?: 0
            val weatherCode = hourly.weather_code?.getOrNull(index) ?: 0


            HourlyForecastUiState(
                hour = hourTime,
                temperature = "$temp°C",
                weatherIconRes = mapWeatherCodeToResource(weatherCode, isNight)
            )
        }

        return fullList.filter {
            val hour = it.hour.substring(0, 2).toIntOrNull() ?: 0
            val minute = it.hour.substring(3, 5).toIntOrNull() ?: 0
            hour > currentHour || (hour == currentHour && minute >= currentMinute)
        }.take(24 - currentHour - 1)
    }

    fun mapWeeklyForecast(daily: WeatherApiResponse.Daily?,isNight: Boolean): List<WeeklyForecastItem> {
        if (daily == null || daily.time == null) return emptyList()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        return daily.time.mapIndexedNotNull { index, date ->
            val parsedDate = sdf.parse(date) ?: return@mapIndexedNotNull null
            calendar.time = parsedDate
            val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) ?: "Day"
            val maxTemp = daily.temperature_2m_max?.getOrNull(index)?.toInt() ?: 0
            val minTemp = daily.temperature_2m_min?.getOrNull(index)?.toInt() ?: 0
            val weatherCode = daily.weather_code?.getOrNull(index) ?: 0

            WeeklyForecastItem(
                day = dayName,
                maxTemp = maxTemp,
                minTemp = minTemp,
                iconResId = mapWeatherCodeToResource(weatherCode, isNight)
            )
        }.take(7)
    }

    private fun mapWeatherCodeToDescription(weatherCode: Int?): String = when (weatherCode) {
        0 -> "Clear sky"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Fog"
        51, 53, 55 -> "Drizzle"
        61, 63, 65 -> "Rain"
        71, 73, 75 -> "Snow"
        95 -> "Thunderstorm"
        else -> "Unknown"
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