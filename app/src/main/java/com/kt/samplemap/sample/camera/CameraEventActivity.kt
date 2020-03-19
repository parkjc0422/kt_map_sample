package com.kt.samplemap.sample.camera

import com.kt.maps.GMap
import com.kt.maps.GMapFragment
import com.kt.samplemap.R
import com.kt.samplemap.sample.SampleActivity

class CameraEventActivity : SampleActivity() {

    override fun layoutId(): Int = R.layout.activity_camera_event

    override fun initContent() {
//        val mapFragment = supportFragmentManager.findFragmentById()
//
//        mapFragment?.run {
//            (this as GMapFragment).setOnMapReadyListener(this@CameraEventActivity)
//        }

    }

    override fun mapFragment(): Int = R.id.cameraMapFragment

    override fun onMapReady(gMap: GMap) {
        super.onMapReady(gMap)
    }

    override fun mapLoadFinish() {

    }
}
