package com.example.weather.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.ui.uiState.WeatherUiState

@Composable
 fun WeatherHeader(
    state: WeatherUiState,
    isScrolled: Boolean,
    isNight: Boolean
) {

    val imageSizeHeight by animateDpAsState(targetValue = if (isScrolled) 112.dp else 200.dp)
    val imageSizeWidth by animateDpAsState(targetValue = if (isScrolled) 124.dp else 220.dp)
    val shapeSize by animateDpAsState(targetValue = if (isScrolled) 150.dp else 250.dp)
    val iconOffsetX by animateDpAsState(targetValue = if (isScrolled) -130.dp else 0.dp)
    val iconOffsetY by animateDpAsState(targetValue = if (isScrolled) (-12).dp else (-12).dp)
    val tempOffsetY by animateDpAsState(targetValue = if (isScrolled) (-150).dp else (-26).dp)
    val tempOffsetX by animateDpAsState(targetValue = if (isScrolled) 100.dp else 0.dp)
Column(modifier = Modifier.padding(horizontal = 12.dp),
    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
    Spacer(modifier = Modifier.height(64.dp))
    LocationInfo(state.currentWeather.locationName,isNight)
    WeatherIcon(
        modifier = Modifier
            .offset(x = iconOffsetX, y = iconOffsetY),
        painter = if (state.currentWeather.weatherIcon != 0) state.currentWeather.weatherIcon else R.drawable.fastwind,
        imageSizeHeight = imageSizeHeight,
        imageSizeWidth = imageSizeWidth,
        shapeSize = shapeSize,
        isNight = state.isNight
    )
    TemperatureInfo(
        modifier = Modifier.offset(x = tempOffsetX, y = tempOffsetY),
        temperature = state.currentWeather.temperature + "°C",
        weatherDescription = state.currentWeather.weatherDescription,
        highTemperature = state.currentWeather.highTemperature + "°C",
        lowTemperature = state.currentWeather.lowTemperature + "°C",
        isNight = state.isNight
    )
}
}

