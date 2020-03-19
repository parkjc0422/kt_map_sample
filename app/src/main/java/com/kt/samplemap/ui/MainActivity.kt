
package com.kt.samplemap.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kt.samplemap.R
import com.kt.samplemap.sample.SampleActivity
import com.kt.samplemap.sample.camera.CameraEventActivity
import com.kt.samplemap.sample.gps.GpsActivity
import com.kt.samplemap.sample.overlay.OverlayActivity
import com.kt.samplemap.ui.adapter.SampleContentAdapter
import com.kt.samplemap.ui.adapter.model.ContentViewModel
import com.kt.samplemap.ui.adapter.model.SampleContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(checkPermission()) {
            grantedPermission()
        }

    }

    /**
     *  init
     */
    fun grantedPermission() {
        val adapter = SampleContentAdapter()
        adapter.contentList.addAll(
            listOf(
                SampleContent("gps") {
                    startSample(GpsActivity::class.java)
                },
                SampleContent("camera") {
                    startSample(CameraEventActivity::class.java)
                },
                SampleContent("overlay") {
                    startSample(OverlayActivity::class.java)
                }
            )
        )
        sampleContentList.adapter = adapter
    }

    private fun <T : SampleActivity> startSample(activity: Class<T>) {
        startActivity(Intent(this@MainActivity, activity))
    }


    fun deniedPermission() {

    }
    /**
     * ================================================================
     *          android device permission
     * ================================================================
     */
    lateinit var permissionList: List<String>
    private fun checkPermission(): Boolean {
         permissionList = arrayListOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).filter {
            !hasPermission(it)
        }

        if(permissionList.isEmpty()) {
            // all of permission is granted
            return true
        }

        requestPermission(permissionList)
        return false
    }


    private fun hasPermission (permissionName: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(applicationContext, permissionName)
    }
    private val permissionRequestCode = 1
    private fun requestPermission(permissionList: List<String>) {
        ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), permissionRequestCode)
    }

    // permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isEmpty()) {
            return
        }

        var resultCode = RESULT_OK
        if (grantResults.size == permissionList.size) {
            val deniedList  = grantResults.filter {
                it != PackageManager.PERMISSION_GRANTED
            }

            if(deniedList.isNotEmpty()) {
                resultCode = Activity.RESULT_CANCELED
            }
        } else {
            resultCode = Activity.RESULT_CANCELED
        }

        if(resultCode == Activity.RESULT_OK) {
            grantedPermission()
        } else {
            deniedPermission()
        }
    }
}
