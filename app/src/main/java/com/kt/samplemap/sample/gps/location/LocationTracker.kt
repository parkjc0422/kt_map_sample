package com.kt.samplemap.sample.gps.location

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.kt.geom.model.LatLng
import com.kt.geom.model.UTMK

/**
 * @property context
 * @property interval   mil Second
 */
class LocationTracker(val context: Context, val interval: Int = 1000, val trackerListener: LocationTrackerListener? = null) :
    GoogleApiClient.ConnectionCallbacks,  GoogleApiClient.OnConnectionFailedListener, LocationListener, SensorEventListener{


    private var client: GoogleApiClient =
        GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
    private var locReq: LocationRequest

    private var sensorManager: SensorManager
    private var accelerometer: Sensor
    private val magnetometer: Sensor

    var isTracking: Boolean

    var mGravity: FloatArray? = null
    var mGeomagnetic: FloatArray? = null
    var eventTimestamp: Long = -1
    var rotationDegree = 0.0f
    var lastLocation: Location? = null

    init {
        locReq = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = this.interval
        }


        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        isTracking = false
        trackerListener?.updateConnectionState(isTracking)
    }

    /**
     * lifeCycle
     */
    public fun onResume() {
        client.connect()
    }

    public fun onPause() {
        if(client.isConnected) {
            turnOffTracking()
            client.disconnect()
        }
    }


    /**
     * public function
     */
    public fun turnOffTracking (result: ((location: Location?, isTurnOn: Boolean)-> Unit)? = null) {
        if (client.isConnected) {
            if(result != null) {
                LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(
                    object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult?) {
                            super.onLocationResult(p0)
                            p0?.run {
                                this@LocationTracker.lastLocation = lastLocation
                                trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
                            }
                        }

                        override fun onLocationAvailability(p0: LocationAvailability?) {
                            super.onLocationAvailability(p0)
                        }
                    }
                )
            } else {
                LocationServices.FusedLocationApi.removeLocationUpdates(client, this)
            }
        }

        sensorManager.unregisterListener(this)

        isTracking = false
        trackerListener?.updateConnectionState(isTracking)
    }

    public fun turnOnTracking(result: ((location: Location?, isTurnOn: Boolean)-> Unit)? = null) {
        if(client.isConnected) {
            if(result != null) {
                LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locReq,
                    object: LocationCallback() {
                        override fun onLocationResult(p0: LocationResult?) {
                            super.onLocationResult(p0)
                            p0?.run {
                                this@LocationTracker.lastLocation = lastLocation
                                trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
                            }
                        }

                        override fun onLocationAvailability(p0: LocationAvailability?) {
                            super.onLocationAvailability(p0)
                        }
                    }, Looper.myLooper()
                )
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(client, locReq, this)
            }


            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)


            if (result != null) {
                val location = LocationServices.getFusedLocationProviderClient(context).lastLocation.result

                if (location == null) {
                    result(location, false)
                    turnOffTracking()
                } else {
                    lastLocation = location
                    result(location, true)
                    trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
                    isTracking = true
                    trackerListener?.updateConnectionState(isTracking)
                }
            } else {
                val location = LocationServices.FusedLocationApi.getLastLocation(client)
                if (location == null) {
                    turnOffTracking()
                    return
                }

                lastLocation = location
                trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
                isTracking = true
                trackerListener?.updateConnectionState(isTracking)
            }
        }
    }


    /**
     *  GoogleApiClient.ConnectionCallbacks
     * @param p0
     */
    override fun onConnected(p0: Bundle?) {
        trackerListener?.locationServiceConnnectState(true)
    }

    /**
     *
     * @param p0
     */
    override fun onConnectionSuspended(p0: Int) {
        trackerListener?.locationServiceConnnectState(false)
    }

    /**
     * GoogleApiClient.OnConnectionFailedListener
     * @param result
     */
    override fun onConnectionFailed(result: ConnectionResult) {
        trackerListener?.locationServiceConnnectState(false)
    }

    /**
     * TODOLocationListener
     * @param location
     */
    override fun onLocationChanged(location: Location) {
        lastLocation = location
        trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
    }

    /**
     * SensorEventListener
     *
     * @param p0
     * @param p1
     */
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_GRAVITY) {
            mGravity = event.values
        }
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values
        }

        if(eventTimestamp > 0
            && event.timestamp - eventTimestamp < (200 * 1000 * 1000).toLong()) {
            return
        }

        if(mGeomagnetic != null && mGravity != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val success =
                SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)
            if (success) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)
                // orientation contains: azimuth, pitch and roll. we use
                // azimuth.
                val angle = Math.toDegrees(orientation[0].toDouble()).toFloat()
                if (Math.abs(rotationDegree - angle) < 1.0f) {
                    return
                }
                rotationDegree = angle
                trackerListener?.updateLocationInfo(lastLocation, rotationDegree)
            }
            eventTimestamp = event.timestamp
        }

    }
}