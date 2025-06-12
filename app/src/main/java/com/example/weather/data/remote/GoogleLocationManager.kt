package com.example.weather.data.remote

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weather.data.model.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.coroutines.cancellation.CancellationException

class GoogleLocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationProvider {
    override suspend fun getCurrentLocation(): LocationModel? {
        try {
            val hasFinePermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasFinePermission) {
                Log.d("GoogleLocationManager","Location not available: Permission ACCESS_FINE_LOCATION not granted")
                return null
            }
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnabled && !isNetworkEnabled) {
                Log.d("GoogleLocationManager","Location not available: GPS and Network are disabled")
                return null
            }

            val location = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
            if (location == null) {
                Log.d("GoogleLocationManager","Location not available: No location data received")
                return null
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            val countryName = if (addresses != null && addresses.isNotEmpty()) {
                addresses[0].countryName ?: "Unknown"
            } else {
                "Unknown"
            }
            Log.d(
                "GoogleLocationManager",
                "Location available: latitude=${location.latitude}, longitude=${location.longitude}, country=$countryName"
            )
            return LocationModel(
                latitude = location.latitude,
                longitude = location.longitude,
                country = countryName
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: SecurityException) {
            Log.d(
                "GoogleLocationManager",
                "Location not available: Security exception - ${e.message}"
            )
            return null
        } catch (e: Exception) {
            Log.d(
                "GoogleLocationManager",
                "Location not available: Unexpected error  - ${e.message}"
            )
            return null
        }
    }
}