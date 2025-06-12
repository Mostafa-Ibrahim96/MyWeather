package com.example.weather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.theme.urbanistFont


@Composable
fun WeeklyForecastItem(
    day: String,
    maxTemp: String,
    minTemp: String,
    weatherIcon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(9.5.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = day,
            modifier = Modifier.weight(1f),
            fontFamily = urbanistFont,
            fontSize = 16.sp,
            letterSpacing = 0.25.sp,
            fontWeight = FontWeight(400),
            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)


        )



        Image(
            modifier = Modifier
                .height(32.dp)
                .weight(1f),
            painter = painterResource(id = weatherIcon),
            contentDescription = "Weather Icon",
            contentScale = ContentScale.Inside

        )


        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,

                )
            {
                Image(
                    painter = painterResource(id = R.drawable.arrowup),
                    contentDescription = "Max Temp Icon",
                    modifier = Modifier
                        .size(12.dp)

                )
                Text(
                    text = maxTemp,
                    fontFamily = urbanistFont,
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onSurface.copy(0.87f)
                )
                Image(
                    painter = painterResource(id = R.drawable.line),
                    contentDescription = "Min Temp Icon",
                    modifier = Modifier.padding(vertical = 5.dp)

                )

                Image(
                    painter = painterResource(id = R.drawable.arrowdown),
                    contentDescription = "Min Temp Icon",
                    modifier = Modifier
                        .size(12.dp)

                )
                Text(
                    text = minTemp,
                    fontFamily = urbanistFont,
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onSurface.copy(0.87f)
                )
            }
        }
    }

}

@Preview(showBackground = true, device = "spec:width=400dp,height=850.9dp,dpi=440")
@Composable
fun WeeklyForecastItemPreview() {
    WeeklyForecastItem(
        day = "Wednesday",
        maxTemp = "32°C",
        minTemp = "20°C",
        weatherIcon = R.drawable.mainlyclear
    )
}