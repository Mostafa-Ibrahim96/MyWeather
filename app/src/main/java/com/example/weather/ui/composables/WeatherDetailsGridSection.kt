package com.example.weather.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.ui.uiState.WeatherDetailsState

@Composable
 fun WeatherDetailsGridSection(weatherDetails: List<WeatherDetailsState>, isNight: Boolean) {
    VerticalSpacer24()
    LazyVerticalGrid(
        modifier = Modifier
            .height(236.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(weatherDetails) { detail ->
            WeatherDetailsItem(
                painter = getIconForDetail(detail),
                value = getLabelForDetail(detail),
                description = when {
                    detail.windSpeed.isNotEmpty() -> detail.windSpeed + " km/h"
                    detail.humidity.isNotEmpty() -> detail.humidity + "%"
                    detail.rain.isNotEmpty() -> detail.rain + "%"
                    detail.uv.isNotEmpty() -> detail.uv
                    detail.pressure.isNotEmpty() -> detail.pressure + " hPa"
                    detail.feelsLike.isNotEmpty() -> detail.feelsLike + "°C"
                    else -> "N/A"
                },
                isNight = isNight
            )


        }
    }
}

private fun getIconForDetail(detail: WeatherDetailsState): Int {
    return when {
        detail.windSpeed.isNotEmpty() -> R.drawable.fastwind
        detail.humidity.isNotEmpty() -> R.drawable.humidity
        detail.rain.isNotEmpty() -> R.drawable.rain
        detail.uv.isNotEmpty() -> R.drawable.uv
        detail.pressure.isNotEmpty() -> R.drawable.arrow_dow
        detail.feelsLike.isNotEmpty() -> R.drawable.temperature
        else -> R.drawable.fastwind
    }
}

private fun getLabelForDetail(detail: WeatherDetailsState): String {
    return when {
        detail.windSpeed.isNotEmpty() -> "Wind"
        detail.humidity.isNotEmpty() -> "Humidity"
        detail.rain.isNotEmpty() -> "Rain"
        detail.uv.isNotEmpty() -> "UV Index"
        detail.pressure.isNotEmpty() -> "Pressure"
        detail.feelsLike.isNotEmpty() -> "Feels like"
        else -> "Unknown"
    }
}