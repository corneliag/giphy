package com.cjuca.giphy.ui.search.data

import com.cjuca.giphy.ui.common.RecyclerItem
import java.util.*

data class SearchGifUiData(val gifList: List<GifRecyclerItem> = emptyList())

enum class SearchViewType(val type: Int) {
    EMPTY_VIEW(1), DISPLAY_VIEW(2), LOADING_MORE_VIEW(3);

    companion object {
        fun valueOf(value: Int): SearchViewType = values().find { it.type == value }!!
    }
}

sealed class GifRecyclerItem : RecyclerItem

object LoadingMoreItem : GifRecyclerItem() {
    private val id: Long = UUID.randomUUID().mostSignificantBits

    override fun getUniqueId(): Long = id
    override fun isSameContent(other: RecyclerItem): Boolean = true
    override fun isSameItem(other: RecyclerItem): Boolean = true
    override fun getType(): Int = SearchViewType.LOADING_MORE_VIEW.type
}

data class EmptyItem(val title: String) : GifRecyclerItem() {
    private val id: Long = UUID.randomUUID().mostSignificantBits

    override fun getUniqueId(): Long = id
    override fun isSameContent(other: RecyclerItem): Boolean = true
    override fun isSameItem(other: RecyclerItem): Boolean = true
    override fun getType(): Int = SearchViewType.EMPTY_VIEW.type
}

data class GifUiItem(val id: String, val title: String, val iconUrl: String) : GifRecyclerItem() {

    override fun getUniqueId(): Long = id.hashCode().toLong()
    override fun isSameItem(other: RecyclerItem): Boolean =
        other is GifUiItem && other.id == id

    override fun isSameContent(other: RecyclerItem): Boolean = other is GifUiItem && other == this

    override fun getType(): Int = SearchViewType.DISPLAY_VIEW.type
}