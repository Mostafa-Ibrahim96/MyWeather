package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.weather.R
import com.example.weather.ui.theme.urbanistFont


@Composable
fun ForecastItem(
    painter: Int,
    temperatureValue: String,
    hour: String,
) {
    Box(
        modifier = Modifier
            .height(132.dp)
            .width(88.dp)
    )
    {
        Box(
            modifier = Modifier
                .height(58.dp)
                .width(64.dp)
                .zIndex(2f)
                .align(
                    Alignment.TopCenter
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(58.dp),
                painter = painterResource(painter),
                contentDescription = "Image",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop

            )
        }

        Box(
            modifier = Modifier
                .height(120.dp)
                .width(88.dp)
                .background(
                    color =MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color =MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(20.dp)
                )
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 62.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TemperatureValue(
                    temperatureValue = temperatureValue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Hour(
                    hour = hour
                )
            }

        }

    }

}


@Composable
fun TemperatureValue(modifier: Modifier = Modifier, temperatureValue: String) {
    Text(
        modifier = modifier,
        text = temperatureValue,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight(500),
        fontFamily = urbanistFont,
        color = MaterialTheme.colorScheme.onSurface.copy(0.87f),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun Hour(modifier: Modifier = Modifier, hour: String) {
    Text(
        modifier = modifier,
        text = hour,
        fontSize = 16.sp,
        fontWeight = FontWeight(500),
        fontFamily = urbanistFont,
        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
    )
}


@Preview(showBackground = true)
@Composable
fun ForecastItemPreview() {
    ForecastItem(
        painter = R.drawable.mainlyclear,
        temperatureValue = "25°",
        hour = "12:00"

    )
}