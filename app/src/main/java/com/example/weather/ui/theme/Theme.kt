package com.example.weather.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1A1A2E),
    surface = Color(0xFF060414),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE5E5E5),
    onSurface = Color(0xFFFFFFFF),

    )

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFF87CEFA),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF060414),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF323232),
    onSurface = Color(0xFF060414)
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}