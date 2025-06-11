package com.example.weather.ui.screens


import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.composables.ForecastItem
import com.example.weather.ui.composables.LoadingScreen
import com.example.weather.ui.composables.LocationInfo
import com.example.weather.ui.composables.TemperatureInfo
import com.example.weather.ui.composables.VerticalSpacer12
import com.example.weather.ui.composables.VerticalSpacer24
import com.example.weather.ui.composables.WeatherDetailsItem
import com.example.weather.ui.composables.WeatherIcon
import com.example.weather.ui.composables.WeeklyForecastItem
import com.example.weather.ui.theme.urbanistFont
import com.example.weather.ui.uiState.HourlyForecastItem
import com.example.weather.ui.uiState.WeatherDetailUiState
import com.example.weather.ui.uiState.WeatherDetailsGrid
import com.example.weather.ui.uiState.WeatherUiState
import com.example.weather.ui.uiState.WeeklyForecastItem
import com.example.weather.ui.viewModel.WeatherViewModel
import org.koin.java.KoinJavaComponent.getKoin


@Composable
fun WeatherScreen(viewModel: WeatherViewModel = getKoin().get()) {
    val state by viewModel.state.collectAsState()
    val weatherDetails by viewModel.weatherDetails.collectAsState()
    val hourlyForecast by viewModel.hourlyForecast.collectAsState()
    val weeklyForecast by viewModel.weeklyForecast.collectAsState()

    val isLoading by remember {
        derivedStateOf {
            state.loading.isLoading || (state.currentWeather.locationName.isEmpty() && state.currentWeather.temperature == "N/A")
        }
    }

    WeatherContent(
        state = state,
        weatherDetails = weatherDetails,
        hourlyForecast = hourlyForecast,
        weeklyForecast = weeklyForecast,
        isLoading = isLoading,
    )
}

@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    state: WeatherUiState = WeatherUiState(),
    weatherDetails: WeatherDetailsGrid = WeatherDetailsGrid(),
    hourlyForecast:List<HourlyForecastItem> = emptyList(),
    weeklyForecast:List<WeeklyForecastItem> = emptyList(),
    isLoading: Boolean,
) {


    val scrollState = rememberScrollState()
    var isScrolled by remember { mutableStateOf(false) }
    LaunchedEffect(scrollState.value) {
        isScrolled = scrollState.value > 50
    }

    val imageSizeHeight by animateDpAsState(targetValue = if (isScrolled) 112.dp else 200.dp)
    val imageSizeWidth by animateDpAsState(targetValue = if (isScrolled) 124.dp else 220.dp)
    val shapeSize by animateDpAsState(targetValue = if (isScrolled) 150.dp else 250.dp)
    val iconOffsetX by animateDpAsState(targetValue = if (isScrolled) 0.dp else 37.dp)
    val iconOffsetY by animateDpAsState(targetValue = if (isScrolled) (-12).dp else (-12).dp)
    val tempOffsetY by animateDpAsState(targetValue = if (isScrolled) (-150).dp else (-26).dp)
    val tempOffsetX by animateDpAsState(targetValue = if (isScrolled) 100.dp else 0.dp)
    Column(
        modifier = if (isSystemInDarkTheme()) {
            modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF060414),
                            Color(0xFF0D0C19)
                        )
                    )
                )
                .verticalScroll(scrollState)
        } else {
            modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF87CEFA),
                            Color(0xFFFFFFFF)
                        )
                    )
                )
                .verticalScroll(scrollState)
        }
    ) {
        if (isLoading) {
            LoadingScreen()
        } else {

            WeatherHeader(
                state = state,
                imageSizeHeight = imageSizeHeight,
                imageSizeWidth = imageSizeWidth,
                shapeSize = shapeSize,
                iconOffsetX = iconOffsetX,
                iconOffsetY = iconOffsetY,
                tempOffsetX = tempOffsetX,
                tempOffsetY = tempOffsetY,
                isScrolled = isScrolled
            )


            Column(
                modifier = Modifier.offset(y = if (isScrolled) (-150).dp else (-18).dp)
            ) {
                WeatherDetailsGridSection(weatherDetails = weatherDetails)
                HourlyForecastSection(hourlyForecast = hourlyForecast)
                WeeklyForecastSection(weeklyForecast = weeklyForecast)
            }
        }
    }

}
@Composable
private fun WeatherHeader(
    state: WeatherUiState,
    imageSizeHeight: Dp,
    imageSizeWidth: Dp,
    shapeSize: Dp,
    iconOffsetX: Dp,
    iconOffsetY: Dp,
    tempOffsetX: Dp,
    tempOffsetY: Dp,
    isScrolled: Boolean
) {
    Spacer(modifier = Modifier.height(64.dp))
    LocationInfo(state.currentWeather.locationName)
    WeatherIcon(
        modifier = Modifier
            .offset(x = iconOffsetX, y = iconOffsetY)
            .padding(start = if (isScrolled) 0.dp else 37.dp),
        painter = if (state.currentWeather.weatherIcon != 0) state.currentWeather.weatherIcon else R.drawable.fastwind,
        imageSizeHeight = imageSizeHeight,
        imageSizeWidth = imageSizeWidth,
        shapeSize = shapeSize
    )
    TemperatureInfo(
        modifier = Modifier.offset(x = tempOffsetX, y = tempOffsetY),
        temperature = state.currentWeather.temperature + "°C",
        weatherDescription = state.currentWeather.weatherDescription,
        highTemperature = state.currentWeather.highTemperature + "°C",
        lowTemperature = state.currentWeather.lowTemperature + "°C"
    )
}


@Composable
private fun WeatherDetailsGridSection(weatherDetails: WeatherDetailsGrid) {
    VerticalSpacer24()
    LazyVerticalGrid(
        modifier = Modifier
            .height(236.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        columns = GridCells.Adaptive(minSize = 115.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(weatherDetails.detailGrid) { detail ->
            WeatherDetailsItem(
                painter = getIconForDetail(detail),
                value = when {
                    detail.windSpeed.isNotEmpty() -> detail.windSpeed + " KM/h"
                    detail.humidity.isNotEmpty() -> detail.humidity + "%"
                    detail.rain.isNotEmpty() -> detail.rain + "%"
                    detail.uv.isNotEmpty() -> detail.uv
                    detail.pressure.isNotEmpty() -> detail.pressure + " hPa"
                    detail.feelsLike.isNotEmpty() -> detail.feelsLike + "°C"
                    else -> "N/A"
                },
                description = getLabelForDetail(detail)
            )
        }
    }
}

@Composable
private fun HourlyForecastSection(hourlyForecast:List<HourlyForecastItem>) {
    VerticalSpacer24()
    Text(
        text = "Today",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold,
        fontFamily = urbanistFont,
        modifier = Modifier.padding(start = 16.dp)
    )
    VerticalSpacer12()
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(hourlyForecast) { (painter, temp,hour) ->

            ForecastItem(
                painter =painter,
                temperatureValue = temp,
                hour = hour
            )
        }
    }
}

@Composable
private fun WeeklyForecastSection(weeklyForecast: List<WeeklyForecastItem>) {
    VerticalSpacer24()
    Text(
        text = "Next 7 days",
        fontSize = 20.sp,
        color = if (isSystemInDarkTheme()) Color.White else Color(0xFF060414),
        fontWeight = FontWeight.SemiBold,
        fontFamily = urbanistFont,
        modifier = Modifier.padding(start = 16.dp)
    )
    VerticalSpacer12()
    LazyColumn(
        modifier = if (isSystemInDarkTheme()) {
            Modifier
                .height(530.dp)
                .padding(horizontal = 16.dp)
                .background(
                    color = Color(0xFF060414).copy(0.7f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(20.dp)
                )
        } else {
            Modifier
                .height(530.dp)
                .padding(horizontal = 16.dp)
                .background(
                    color = Color(0xFFFFFFFF).copy(0.7f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(20.dp)
                )
        },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(weeklyForecast.size + weeklyForecast.size - 1) { index ->
            if (index % 2 == 0) {
                val itemIndex = index / 2
                WeeklyForecastItem(
                    day = weeklyForecast[itemIndex].day,
                    maxTemp = "${weeklyForecast[itemIndex].maxTemp}°C",
                    minTemp = "${weeklyForecast[itemIndex].minTemp}°C",
                    weatherIcon = weeklyForecast[itemIndex].iconResId
                )
            } else {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.08f),
                    thickness = 1.dp
                )
            }
        }
    }
}


private fun getIconForDetail(detail: WeatherDetailUiState): Int {
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

private fun getLabelForDetail(detail: WeatherDetailUiState): String {
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

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(getKoin().get())
}