package com.kt.samplemap.sample.util

import android.location.Location
import com.kt.geom.model.LatLng
import com.kt.geom.model.UTMK

object LocationUtils {
    /**
     * Location to Utmk Coordination
     *
     * @param location
     * @return
     */
    fun locationToUtmk(location: Location): UTMK{
        return UTMK.valueOf(LatLng(location.latitude, location.longitude))
    }
}