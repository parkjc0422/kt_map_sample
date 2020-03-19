package com.kt.samplemap.sample.gps.location

import android.location.Location
import com.google.android.gms.common.ConnectionResult

interface LocationTrackerListener {
    fun updateLocationInfo(location: Location?, degree: Float)
    fun updateConnectionState(isConnect: Boolean)
    fun locationServiceConnnectState(isConnect: Boolean)
}