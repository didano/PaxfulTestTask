package com.example.paxfultesttask.utils

import android.content.Context
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector

class ShakeDetectionUtil(private val context: Context){
    private var detector: ShakeDetector? = null
    private var sensorManager: SensorManager? = null

    fun init(listener: ShakeDetector.Listener) {
        if (detector == null) {
            detector = ShakeDetector(listener).apply {
                setSensitivity(ShakeDetector.SENSITIVITY_MEDIUM)
            }
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        }
        detector
    }

    fun start() {
        detector?.start(sensorManager)
    }

    fun stop() {
        detector?.stop()
    }
}