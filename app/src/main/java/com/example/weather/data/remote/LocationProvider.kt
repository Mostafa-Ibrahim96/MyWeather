package com.example.myweather.data.remote

import com.example.weather.data.model.LocationModel

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationModel?
}