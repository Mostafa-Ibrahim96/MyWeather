package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R

import com.example.weather.ui.theme.urbanistFont

@Composable
fun TemperatureInfo(
    modifier: Modifier= Modifier,
    temperature: String,
    weatherDescription: String,
    highTemperature: String,
    lowTemperature: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = temperature,
            fontSize = 64.sp,
            fontFamily = urbanistFont,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight(600),
            letterSpacing = 0.25.sp
        )
        Text(
            text = weatherDescription,
            fontSize = 16.sp,
            fontFamily = urbanistFont,
            fontWeight = FontWeight(500),
            letterSpacing = 0.25.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .height(35.dp)
                .width(168.dp)
                .background(
                    color = Color(0xFF060414).copy(alpha = 0.08f),
                    shape = RoundedCornerShape(100.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrowup),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = highTemperature,
                    fontSize = 16.sp,
                    fontFamily = urbanistFont,
                    fontWeight = FontWeight(500),
                    letterSpacing = 0.25.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
                Image(
                    painter = painterResource(id = R.drawable.line),
                    contentDescription = "Weather Icon",
                    Modifier.padding(vertical = 10.5.dp, horizontal = 8.dp)

                )

                Image(
                    painter = painterResource(id = R.drawable.arrowdown),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = lowTemperature,
                    fontSize = 16.sp,
                    fontFamily = urbanistFont,
                    fontWeight = FontWeight(500),
                    letterSpacing = 0.25.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color(0xFF060414).copy(alpha = 0.6f),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TemperatureInfoPreview() {
    TemperatureInfo(
        temperature = "25°C",
        weatherDescription = "Partly cloudy",
        highTemperature = "30°C",
        lowTemperature = "20°C"
    )
}