package com.cjuca.giphy.ui.search.recyclerviewholder

import androidx.recyclerview.widget.RecyclerView
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.GifItemViewHolderBinding
import com.cjuca.giphy.ui.search.data.GifUiItem
import com.cjuca.giphy.util.loadImage

class GifItemRecyclerViewHolder(
    private val binding: GifItemViewHolderBinding,
    private val actionGifClick: (String) -> Unit = {}
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: GifUiItem) {
        binding.title.text = data.title
        binding.gif.loadImage(data.iconUrl, R.drawable.ic_baseline_image_search)
        binding.root.setOnClickListener {
            actionGifClick.invoke(data.iconUrl)
        }
    }
}