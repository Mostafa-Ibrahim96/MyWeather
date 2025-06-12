package com.example.weather.data.remote


object ApiConfig {
    const val BASE_URL = "https://api.open-meteo.com/v1"
    const val WEATHER_ENDPOINT = "/forecast"
    const val HOURLY = "temperature_2m,weather_code&models=icon_seamless"
    const val WEATHER_DAILY_PARAMETER = "temperature_2m_max,temperature_2m_min,weather_code"
    const val WEATHER_CURRENT_PARAMETER = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
}