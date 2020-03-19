package com.kt.samplemap.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kt.samplemap.ui.adapter.model.ContentViewModel

abstract class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view){
    abstract fun bindView(model: ContentViewModel)
}