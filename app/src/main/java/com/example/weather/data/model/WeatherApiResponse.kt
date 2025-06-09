package com.example.weather.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiResponse(
    val current: Current? = null,
    val current_units: CurrentUnits? = null,
    val daily: Daily? = null,
    val daily_units: DailyUnits? = null,
    val elevation: Double? = null,
    val generationtime_ms: Double? = null,
    val hourly: Hourly? = null,
    val hourly_units: HourlyUnits? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val timezone: String? = null,
    val timezone_abbreviation: String? = null,
    val utc_offset_seconds: Int? = null
) {
    @Serializable
    data class Current(
        val apparent_temperature: Double? = null,
        val cloud_cover: Int? = null,
        val interval: Int? = null,
        val is_day: Int? = null,
        val precipitation: Double? = null,
        val pressure_msl: Double? = null,
        val rain: Double? = null,
        val relative_humidity_2m: Int? = null,
        val showers: Double? = null,
        val snowfall: Double? = null,
        val surface_pressure: Double? = null,
        val temperature_2m: Double? = null,
        val time: String? = null,
        val weathercode: Int? = null,
        val wind_direction_10m: Int? = null,
        val wind_gusts_10m: Double? = null,
        val wind_speed_10m: Double? = null
    )
    @Serializable
    data class CurrentUnits(
        val apparent_temperature: String? = null,
        val cloud_cover: String? = null,
        val interval: String? = null,
        val is_day: String? = null,
        val precipitation: String? = null,
        val pressure_msl: String? = null,
        val rain: String? = null,
        val relative_humidity_2m: String? = null,
        val showers: String? = null,
        val snowfall: String? = null,
        val surface_pressure: String? = null,
        val temperature_2m: String? = null,
        val time: String? = null,
        val weathercode: String? = null,
        val wind_direction_10m: String? = null,
        val wind_gusts_10m: String? = null,
        val wind_speed_10m: String? = null
    )
    @Serializable
    data class Daily(
        val temperature_2m_max: List<Double>? = null,
        val temperature_2m_min: List<Double>? = null,
        val time: List<String>? = null,
        val weathercode: List<Int>? = null
    )
    @Serializable
    data class DailyUnits(
        val temperature_2m_max: String? = null,
        val temperature_2m_min: String? = null,
        val time: String? = null,
        val weathercode: String? = null
    )
    @Serializable
    data class Hourly(
        val temperature_2m: List<Double>? = null,
        val time: List<String>? = null,
        val weathercode: List<Int>? = null
    )
    @Serializable
    data class HourlyUnits(
        val temperature_2m: String? = null,
        val time: String? = null,
        val weathercode: String? = null
    )
}