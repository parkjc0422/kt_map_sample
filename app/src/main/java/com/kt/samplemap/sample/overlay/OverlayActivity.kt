package com.kt.samplemap.sample.overlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kt.samplemap.R
import com.kt.samplemap.sample.SampleActivity

class OverlayActivity : SampleActivity() {

    override fun layoutId(): Int = R.layout.activity_overlay

    override fun initContent() {

    }

    override fun mapLoadFinish() {

    }

    override fun mapFragment(): Int = R.id.overlayMapFragment
}
