package com.example.weather

import android.app.Application
import com.example.weather.data.di.appModule
import com.example.weather.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule, viewModelModule)
        }
    }
}