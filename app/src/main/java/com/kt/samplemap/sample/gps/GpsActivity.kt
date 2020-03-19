package com.kt.samplemap.sample.gps

import android.location.Location
import com.kt.maps.GMapFragment
import com.kt.maps.ViewpointChange
import com.kt.samplemap.R
import com.kt.samplemap.sample.SampleActivity
import com.kt.samplemap.sample.gps.location.LocationTracker
import com.kt.samplemap.sample.gps.location.LocationTrackerListener
import com.kt.samplemap.sample.util.LocationUtils
import kotlinx.android.synthetic.main.activity_gps.*

class GpsActivity : SampleActivity(), LocationTrackerListener{
    private var locationTracker: LocationTracker? = null
    override fun layoutId(): Int = R.layout.activity_gps

    override fun initContent() {
        locationTracker = LocationTracker(context = this,interval = 2000, trackerListener = this)

        btnLocationTracking.setOnClickListener {
            locationTracker?.let {
                if(it.isTracking) {
                    it.turnOffTracking()
                    btnLocationTracking.setBackgroundResource(R.drawable.gps_off)
                } else {
                    it.turnOnTracking()
                    btnLocationTracking.setBackgroundResource(R.drawable.gps_on)
                }
            }
        }
    }

    override fun mapFragment(): Int = R.id.gpsMapFragment

    override fun mapLoadFinish() {
        showMsg("mapLoadFinish")
        locationTracker?.turnOnTracking()
    }

    override fun onResume() {
        super.onResume()
        locationTracker?.onResume()
    }

    override fun onPause() {
        super.onPause()
        locationTracker?.onPause()
    }

    override fun updateLocationInfo(location: Location?, degree: Float) {
        val viewpointChange = ViewpointChange
            .Builder()

        viewpointChange.rotateTo(degree.toDouble())
        location?.run {
            viewpointChange.panTo(LocationUtils.locationToUtmk(this))
        }


        gMap?.animate(viewpointChange.build(), 0)
    }


    override fun updateConnectionState(isConnect: Boolean) {
        if(isConnect) {
            btnLocationTracking.setBackgroundResource(R.drawable.gps_on)
        } else {
            btnLocationTracking.setBackgroundResource(R.drawable.gps_off)
        }
    }

    override fun locationServiceConnnectState(isConnect: Boolean) {}
}
