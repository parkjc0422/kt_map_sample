package com.kt.samplemap.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.kt.samplemap.R
import com.kt.samplemap.ui.adapter.model.ContentViewModel
import com.kt.samplemap.ui.adapter.model.SampleContent
import com.kt.samplemap.ui.adapter.viewholder.ContentViewHolder
import kotlinx.android.synthetic.main.sample_content.view.*

class SampleContentViewHolder(view: View): ContentViewHolder(view){
    val contentTitle: TextView = view.findViewById(R.id.sampleBrief)

    override fun bindView(model: ContentViewModel) {
        if (model is SampleContent) {
            contentTitle.text = model.title

            contentTitle.setOnClickListener {
                model.event(model)
            }
        }
    }
}