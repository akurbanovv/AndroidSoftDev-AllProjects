package com.example.pokemonapp

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MotionController(internal var mapsActivity: MapsActivity) : SensorEventListener {

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.equals(accSensor)!!){
            if (Math.abs(p0.values[1]) > 15){
                mapsActivity.updatePokemon()
            }
        }
    }

    fun register(){
        if (accSensor != null){
            sensorManager?.registerListener(
                this, accSensor, SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun unregister(){
       sensorManager?.unregisterListener(this)
    }

    var sensorManager: SensorManager? = null
    var accSensor: Sensor? = null

    init {
        sensorManager = mapsActivity.getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }


}