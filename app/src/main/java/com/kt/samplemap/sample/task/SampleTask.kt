package com.kt.samplemap.sample.task

import com.kt.maps.GMap

interface SampleTask {
    fun execute(gmap: GMap)
    fun endTask()
}