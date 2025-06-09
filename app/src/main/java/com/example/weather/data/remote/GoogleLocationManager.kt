package com.example.myweather.data.remote

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.weather.data.model.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.tasks.await
class GoogleLocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationProvider {


    override suspend fun getCurrentLocation(): LocationModel? {
        try {
            // Check permission for fine location
            val hasFinePermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasFinePermission) {
                return null // No permission, return null
            }
            // Check if GPS or network is enabled
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnabled && !isNetworkEnabled) {
                return null // No GPS or network, return null
            }

            // Get current location with high accuracy
            val location = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()

            // Check if location is null
            if (location == null) {
                return null // No location found
            }

            // Return location data
            return LocationModel(
                latitude = location.latitude,
                longitude = location.longitude
            )
        } catch (e: CancellationException) {
            throw e // Respect coroutine cancellation
        } catch (e: SecurityException) {
            return null // Permission denied or other security issue
        } catch (e: Exception) {
            return null // Unexpected error
        }
    }
}