package com.kt.samplemap.sample.overlay.testable

import com.kt.geom.model.UTMK
import com.kt.maps.GMap
import com.kt.maps.model.Point
import com.kt.maps.overlay.Marker
import com.kt.maps.overlay.MarkerCaptionOptions
import com.kt.maps.overlay.MarkerOptions
import com.kt.maps.overlay.Overlay
import com.kt.samplemap.sample.task.SampleTask

class MarkerOverlayTestable: SampleTask {
    private var overlayList = arrayListOf<Overlay>()
    private var map: GMap? = null


    override fun execute(gmap: GMap) {
        this.map = gmap
    }

    fun addDefaultMarker() {
        val position = UTMK(959640.0, 1943858.0)
        val marker = Marker(
            MarkerOptions().position(position).captionOptions(
                MarkerCaptionOptions().setTitle("DEFAULT MARKER!")
            )
        )
        marker.title = "markerId: " + marker.id
        marker.iconSize = Point(60.0, 60.0)

        overlayList.add(marker)
        map?.addOverlay(marker)
    }



    override fun endTask() {
        this.map?.run {
            overlayList.forEach {
                this.removeOverlay(it)
            }
        }
    }
}