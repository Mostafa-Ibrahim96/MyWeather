package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.theme.urbanistFont


@Composable
fun LocationInfo(locationName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        ) {
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = locationName,
            fontFamily = urbanistFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.25.sp,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 20.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LocationInfoPreview() {
    LocationInfo(
        locationName = "New York City"
    )
}