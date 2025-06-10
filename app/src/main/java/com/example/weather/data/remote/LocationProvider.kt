package com.example.weather.data.remote

import com.example.weather.data.model.LocationModel

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationModel?
}