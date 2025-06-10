package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weather.R


@Composable
fun WeatherIcon(
    modifier: Modifier = Modifier,
    painter: Int,
    imageSizeHeight: Dp = 200.dp,
    imageSizeWidth: Dp = 220.21.dp,
    shapeSize: Dp = 250.dp
) {
    Box(
        modifier = modifier.size(shapeSize),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(painter),
            contentDescription = "Weather Icon",
            modifier = Modifier
                .height(imageSizeHeight)
                .width(imageSizeWidth)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherIconPreview() {
    WeatherIcon(
        painter = R.drawable.mainlyclear // Replace with your drawable resource
    )
}