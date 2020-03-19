package com.kt.samplemap

import android.app.Application
import com.kt.maps.GMapShared
import com.kt.samplemap.config.MapConfigureProvider

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MapConfigureProvider.setMapKey(applicationContext)
        GMapShared.getInstance(applicationContext)
    }
}