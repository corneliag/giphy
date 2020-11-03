package com.cjuca.giphy.ui.search.data

import android.content.Context
import com.cjuca.giphy.R
import com.cjuca.giphy.service.model.Gif
import java.util.*

class SearchGifUiMapper(context: Context) {

    val emptyList = listOf(EmptyItem(context.getString(R.string.empty_gif_list)))
    val searchNoResult = listOf(EmptyItem(context.getString(R.string.search_list_no_result)))

    fun mapGifListToUiData(gifList: List<Gif>, searchValue: String): List<GifRecyclerItem> {
        return if (searchValue.isNotEmpty() && gifList.isEmpty()) {
            searchNoResult
        } else if (gifList.isEmpty()) {
            emptyList
        } else gifList.map { gif ->
            GifUiItem(gif.id, gif.title, gif.images.fixed_width?.url ?: "")
        }
    }
}
