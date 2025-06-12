package com.example.weather.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.theme.urbanistFont
import com.example.weather.ui.uiState.WeeklyForecastItem

@Composable
fun WeeklyForecastCard(
    forecasts: List<WeeklyForecastItem>,
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
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


    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface.copy(0.7f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {

        forecasts.forEachIndexed { index, forecast ->

            WeeklyForecastItem(
                day = forecast.day,
                maxTemp = forecast.maxTemp.toString()+"°C",
                minTemp = forecast.minTemp.toString()+"°C",
                weatherIcon = forecast.iconResId
            )


            if (index < forecasts.size - 1) {

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.08f),
                    thickness = 1.dp
                )
            }

        }
    }
}
}


@Preview
@Composable
fun WeeklyForecastCardPreview() {
    WeeklyForecastCard(
        forecasts = listOf(
            WeeklyForecastItem("Mon", 30, 20, R.drawable.clear_sky),
            WeeklyForecastItem("Tue", 28, 18, R.drawable.clear_sky),
            WeeklyForecastItem("Wed", 25, 15, R.drawable.clear_sky),
            WeeklyForecastItem("Thu", 27, 17, R.drawable.clear_sky),
            WeeklyForecastItem("Fri", 29, 19, R.drawable.clear_sky),
            WeeklyForecastItem("Sat", 31, 21, R.drawable.clear_sky),
            WeeklyForecastItem("Sun", 26, 16, R.drawable.clear_sky)
        )
    )
}