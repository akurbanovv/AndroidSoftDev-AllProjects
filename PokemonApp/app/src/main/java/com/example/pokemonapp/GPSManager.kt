package com.example.pokemonapp

import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

class GPSManager(internal var mapsActivity: MapsActivity) : LocationCallback() {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var currentLocation: Location? = null
    var locationRequest: LocationRequest = LocationRequest.create()


    override fun onLocationResult(p0: LocationResult?) {
        currentLocation = p0?.lastLocation
        currentLocation?.run {
            mapsActivity.updateCurrentLocation(this)
        }
    }

    fun register() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            currentLocation = it
            mapsActivity.updateCurrentLocation(currentLocation)
        }

        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            this,
            Looper.myLooper()
        )
    }

    fun unregister(){
        fusedLocationProviderClient!!.removeLocationUpdates(this)
    }

    init {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000L
        locationRequest.fastestInterval = 1000L
        locationRequest.smallestDisplacement = 5f

        var builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(mapsActivity)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mapsActivity)
    }


}