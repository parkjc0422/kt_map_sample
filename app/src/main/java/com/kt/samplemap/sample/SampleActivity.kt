package com.kt.samplemap.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kt.maps.GMap
import com.kt.maps.GMapFragment
import com.kt.maps.GMapResultCode
import com.kt.maps.OnMapReadyListener
import com.kt.samplemap.R

abstract class SampleActivity: AppCompatActivity (), OnMapReadyListener{
    var gMap: GMap? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())


        fragmentManager.findFragmentById(mapFragment()).run {
            (this as GMapFragment).setOnMapReadyListener(this@SampleActivity)
        }


        initContent()
    }

    override fun onFailReadyMap(code: GMapResultCode) {
    }

    override fun onMapReady(gMap: GMap) {
        this.gMap = gMap
        mapLoadFinish()
    }

    abstract fun layoutId(): Int

    abstract fun initContent()

    abstract fun mapFragment(): Int

    abstract fun mapLoadFinish()


    fun showMsg(msg:String) {
        Log.d("Toast", "msg: $msg")
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show()
    }
}