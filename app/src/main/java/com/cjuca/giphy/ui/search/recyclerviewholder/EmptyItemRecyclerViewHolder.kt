package com.cjuca.giphy.ui.search.recyclerviewholder

import androidx.recyclerview.widget.RecyclerView
import com.cjuca.giphy.databinding.EmptyItemViewHolderBinding
import com.cjuca.giphy.ui.search.data.EmptyItem

class EmptyItemRecyclerViewHolder(private val binding: EmptyItemViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: EmptyItem) {
        binding.text.text = data.title
    }
}