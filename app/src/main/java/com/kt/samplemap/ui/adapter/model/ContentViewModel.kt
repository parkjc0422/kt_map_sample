package com.kt.samplemap.ui.adapter.model

/**
 *  viewType = 1
 *  viewType = 2
 * @property viewType
 */
open class ContentViewModel(val viewType: Int,
                            val event:(ContentViewModel)->Unit)