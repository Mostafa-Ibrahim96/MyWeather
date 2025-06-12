package com.example.weather.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.ui.theme.urbanistFont
import com.example.weather.ui.uiState.HourlyForecastUiState

@Composable
 fun HourlyForecastSection(hourlyForecast:List<HourlyForecastUiState>) {
    VerticalSpacer24()
    Text(
        text = "Today",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold,
        fontFamily = urbanistFont,
        modifier = Modifier.padding(start = 12.dp)
    )
    VerticalSpacer12()
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(hourlyForecast) { (painter, temp,hour) ->

            ForecastItem(
                hourlyForecastUiState = HourlyForecastUiState(
                    weatherIconRes = painter,
                    temperature = temp,
                    hour = hour
                )
            )
        }
    }
}