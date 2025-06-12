package com.example.weather.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.weather.ui.composables.HourlyForecastSection
import com.example.weather.ui.composables.LoadingScreen
import com.example.weather.ui.composables.WeatherDetailsGridSection
import com.example.weather.ui.composables.WeatherHeader
import com.example.weather.ui.composables.WeeklyForecastCard
import com.example.weather.ui.uiState.HourlyForecastUiState
import com.example.weather.ui.uiState.WeatherDetailsState
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

    WeatherContent(
        state = state,
        weatherDetails = weatherDetails,
        hourlyForecast = hourlyForecast,
        weeklyForecast = weeklyForecast,
    )
}

@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    state: WeatherUiState = WeatherUiState(),
    weatherDetails: List<WeatherDetailsState> = emptyList(),
    hourlyForecast: List<HourlyForecastUiState> = emptyList(),
    weeklyForecast: List<WeeklyForecastItem> = emptyList(),
) {

    val scrollState = rememberScrollState()

    val isScrolled by remember {
        derivedStateOf {
            scrollState.value > 50
        }
    }

    val isNight = state.isNight

    val backgroundBrush = if (isSystemInDarkTheme() || isNight) {
        Brush.linearGradient(colors = listOf(Color(0xFF060414), Color(0xFF0D0C19)))
    } else {
        Brush.linearGradient(colors = listOf(Color(0xFF87CEFA), Color(0xFFFFFFFF)))
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(brush = backgroundBrush)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.loading.isLoading) {
            LoadingScreen()
        } else {
            WeatherHeader(
                isNight = isNight,
                state = state,
                isScrolled = isScrolled
            )
            Column(
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = if (isScrolled) (-150).dp.roundToPx() else (-18).dp.roundToPx()
                    )
                }
            ) {
                WeatherDetailsGridSection(weatherDetails = weatherDetails, isNight)
                HourlyForecastSection(hourlyForecast = hourlyForecast, isNight)
                WeeklyForecastCard(forecasts = weeklyForecast, isNight)
            }
        }
    }

}
