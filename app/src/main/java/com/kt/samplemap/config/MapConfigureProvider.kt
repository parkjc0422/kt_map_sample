package com.kt.samplemap.config

import android.content.Context
import com.kt.maps.GMapResultCode
import com.kt.maps.GMapShared
import com.kt.maps.util.GMapKeyManager
import java.security.AccessControlContext

object MapConfigureProvider  {
    private val MapKey = "D6EBB237BDFF4F89F0752AFC00C7B26EEB3B215412341234".toLowerCase()

    fun setMapKey (context: Context): GMapResultCode = GMapKeyManager.getInstance().init(context, MapKey)
}