package com.example.myweather.data.di

import com.example.weather.ui.viewModel.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get(),get()) }
}