package com.kt.samplemap.sample.overlay.testable

import android.graphics.Color
import com.kt.geom.model.UTMK
import com.kt.maps.GMap
import com.kt.maps.overlay.*
import com.kt.samplemap.sample.task.SampleTask

class PolygonOverlayTestable: SampleTask {
    private var map: GMap? = null
    private var overlayList = arrayListOf<Overlay>()
    override fun execute(gmap: GMap) {
        this.map = gmap
        addPolygon()
        addCircle()
        addPolyLine()
    }

    fun addPolygon() {
        val polygon = Polygon(
            PolygonOptions()
                .addPoints(                                                                 // Exterior
                    UTMK(956744.0, 1943444.0), UTMK(956652.0, 1943284.0),
                    UTMK(956410.0, 1943304.0), UTMK(956588.0, 1943122.0),
                    UTMK(956562.0, 1942908.0), UTMK(956742.0, 1943024.0),
                    UTMK(956922.0, 1942918.0), UTMK(956882.0, 1943120.0),
                    UTMK(957040.0, 1943292.0), UTMK(956832.0, 1943296.0)
                )
                .addHole(                                                                   // interior
                    UTMK(956652.0, 1943284.0), UTMK(956588.0, 1943122.0),
                    UTMK(956742.0, 1943024.0), UTMK(956652.0, 1943284.0)
                )
                .fillColor(Color.YELLOW)                                                    // 내부 색상
                .strokeColor(Color.GREEN)                                                   // 윤곽선의 색상
                .strokeWidth(1)                                                  // 윤곽선의 두께
        )

        overlayList.add(polygon)
        map?.addOverlay(polygon)
    }


    fun addCircle() {
        val circle = Circle(
            CircleOptions()
                .radius(500)                                                        // 반지름
                .origin(UTMK(956744.0, 1940444.0))                                  // 중심점
                .fillColor(Color.RED)                                                      // 내부 색상
        )

        overlayList.add(circle)
        map?.addOverlay(circle)
    }


    fun addPolyLine() {
        val polyline = Polyline(
            PolylineOptions()
                .addPoints(UTMK(951590.0, 1940834.0), UTMK(955190.0, 1940134.0))
                .addPoint(UTMK(953690.0, 1942134.0))
                .color(Color.RED)
                .width(5)
        )
        overlayList.add(polyline)
        map?.addOverlay(polyline)
    }

    override fun endTask() {
        this.map?.run {
            overlayList.forEach {
                this.removeOverlay(it)
            }
        }
    }
}