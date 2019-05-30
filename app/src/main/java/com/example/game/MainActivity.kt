package com.example.game

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*

class MainActivity : Activity() , SensorEventListener {

    companion object{
        const val PROXIMITY = 2.5
    }
    private var accelerometer_manager : SensorManager ?= null
    private var accelerometer_sensor : Sensor ?= null
    private var proximity_manager : SensorManager ?= null
    private var proximity_sensor : Sensor ?= null
    var ground : GameView ?= null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        accelerometer_manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer_sensor = accelerometer_manager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        proximity_manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximity_sensor = proximity_manager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ground = GameView(this)
        setContentView(ground)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            ground!!.updateGameView(event.values[1]*0.1F , event.values[0]*0.1F)
        }
        if (event!= null && event.sensor.type == Sensor.TYPE_PROXIMITY){
            if (event.values[0] >= -PROXIMITY && event.values[0] <= PROXIMITY) ground!!.updateProximity()
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer_manager!!.registerListener(this,accelerometer_sensor, SensorManager.SENSOR_DELAY_GAME)
        proximity_manager!!.registerListener(this,proximity_sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        accelerometer_manager!!.unregisterListener(this)
        proximity_manager!!.unregisterListener(this)
    }

}

