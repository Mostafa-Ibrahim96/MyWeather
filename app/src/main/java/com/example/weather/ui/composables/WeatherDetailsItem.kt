package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun WeatherDetailsItem(
    modifier: Modifier= Modifier,
    painter: Int,
    value: String,
    description: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(115.dp)
            .background(
                color =MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color =MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(24.dp)
            )   . padding
                (
                top = 16.dp,
                bottom = 16.dp,
                end = 8.dp,
                start = 8.dp
            ) ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(painter),
            contentDescription = "Weather Icon",
            modifier = Modifier
                .size(32.dp)

        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = value,
            fontSize = 20.sp,
            fontFamily = urbanistFont,
            fontWeight = FontWeight(500),
            letterSpacing = 0.25.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
        )
        Text(
            text = description,
            fontSize = 14.sp,
            fontFamily = urbanistFont,
            fontWeight = FontWeight(400),
            letterSpacing = 0.25.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

    }
}
@Composable
fun WeatherDetailsItemRow(modifier: Modifier= Modifier,
                          painter: Int,
                          value: String,
                          description: String) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),

        ) {
        WeatherDetailsItem(
            modifier = Modifier.weight(1f),
            painter = painter,
            value = value,
            description = description
        )
        WeatherDetailsItem(
            modifier = Modifier.weight(1f),
            painter = painter,
            value = value,
            description = description
        )
        WeatherDetailsItem(
            modifier = Modifier.weight(1f),
            painter = painter,
            value = value,
            description = description
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailsItemPreview() {
    WeatherDetailsItem(
        painter = R.drawable.humidity,
        value = "60%",
        description = "Humidity"
    )
}



