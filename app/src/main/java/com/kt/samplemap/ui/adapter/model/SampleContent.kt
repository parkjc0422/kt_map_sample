package com.kt.samplemap.ui.adapter.model

import android.app.Activity
import com.kt.samplemap.sample.SampleActivity
import com.kt.samplemap.ui.adapter.SampleContentAdapter

class SampleContent (
    var title: String,
    event:(ContentViewModel)->Unit
): ContentViewModel(SampleContentAdapter.ContentViewType, event)