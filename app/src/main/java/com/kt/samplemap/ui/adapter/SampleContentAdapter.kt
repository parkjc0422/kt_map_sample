package com.kt.samplemap.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kt.samplemap.R
import com.kt.samplemap.ui.adapter.model.ContentViewModel
import com.kt.samplemap.ui.adapter.viewholder.ContentViewHolder
import com.kt.samplemap.ui.adapter.viewholder.EmptyContentViewHolder
import com.kt.samplemap.ui.adapter.viewholder.SampleContentViewHolder

class SampleContentAdapter : RecyclerView.Adapter<ContentViewHolder>() {
    companion object {
        const val EmptyContentViewType = 1
        const val ContentViewType = 2
    }
    var contentList: MutableList<ContentViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            EmptyContentViewType -> {
                EmptyContentViewHolder(inflater.inflate(R.layout.empty_content, parent, false))
            }
            ContentViewType-> {
                SampleContentViewHolder(inflater.inflate(R.layout.sample_content, parent, false))
            }
            else -> {
                EmptyContentViewHolder(inflater.inflate(R.layout.empty_content, parent, false))
            }
        }
    }

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        if(contentList.isEmpty()) {

        } else {
            holder.bindView(contentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        if(contentList.isEmpty()) EmptyContentViewType else {
            ContentViewType
        }
}