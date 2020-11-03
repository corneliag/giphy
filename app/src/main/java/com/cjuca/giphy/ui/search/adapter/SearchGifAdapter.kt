package com.cjuca.giphy.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cjuca.giphy.databinding.EmptyItemViewHolderBinding
import com.cjuca.giphy.databinding.GifItemViewHolderBinding
import com.cjuca.giphy.databinding.LoadingItemViewHolderBinding
import com.cjuca.giphy.ui.search.data.*
import com.cjuca.giphy.ui.search.recyclerviewholder.EmptyItemRecyclerViewHolder
import com.cjuca.giphy.ui.search.recyclerviewholder.GifItemRecyclerViewHolder
import com.cjuca.giphy.ui.search.recyclerviewholder.LoadingItemRecyclerViewHolder

class SearchGifAdapter(private val actionGifClick: (String) -> Unit = {}) :
    ListAdapter<GifRecyclerItem, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<GifRecyclerItem>() {

            override fun areItemsTheSame(
                oldItem: GifRecyclerItem,
                newItem: GifRecyclerItem
            ): Boolean {
                if (oldItem.getType() == newItem.getType()) {
                    return oldItem.isSameItem(newItem)
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: GifRecyclerItem,
                newItem: GifRecyclerItem
            ): Boolean {
                return oldItem.isSameContent(newItem)
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (SearchViewType.valueOf(viewType)) {
            SearchViewType.EMPTY_VIEW -> {
                EmptyItemRecyclerViewHolder(
                    EmptyItemViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            SearchViewType.DISPLAY_VIEW -> {
                GifItemRecyclerViewHolder(
                    GifItemViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), actionGifClick
                )
            }
            SearchViewType.LOADING_MORE_VIEW -> {
                LoadingItemRecyclerViewHolder(
                    LoadingItemViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = getItem(position)) {
            is LoadingMoreItem -> {
                //nothing to do
            }
            is EmptyItem -> {
                val emptyTask = holder as EmptyItemRecyclerViewHolder
                emptyTask.bind(data)
            }
            is GifUiItem -> {
                (holder as GifItemRecyclerViewHolder).bind(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).getUniqueId()
    }
}