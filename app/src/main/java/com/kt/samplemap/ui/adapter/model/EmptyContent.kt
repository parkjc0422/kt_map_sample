package com.kt.samplemap.ui.adapter.model

import com.kt.samplemap.ui.adapter.SampleContentAdapter

class EmptyContent(event:(ContentViewModel)->Unit): ContentViewModel(SampleContentAdapter.EmptyContentViewType, event)