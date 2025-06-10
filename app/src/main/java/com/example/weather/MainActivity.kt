package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.myweather.data.di.viewModelModule
import com.example.weather.data.di.appModule
import com.example.weather.ui.screens.WeatherScreen
import com.example.weather.ui.theme.WeatherTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = false




        GlobalContext.startKoin {
            androidContext(this@MainActivity)
            modules(appModule, viewModelModule)
        }
        setContent {
            WeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(

                    )
                }
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {
        WeatherScreen()
    }
}