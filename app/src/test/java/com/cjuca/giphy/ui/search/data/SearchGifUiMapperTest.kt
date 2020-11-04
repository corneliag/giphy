package com.cjuca.giphy.ui.search.data

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cjuca.giphy.CommonMockData
import com.cjuca.giphy.R
import com.cjuca.giphy.service.model.Gif
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SearchGifUiMapperTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val mapper = SearchGifUiMapper(context)

    @Test
    fun mapGifListToUiData_emptyList() {
        // GIVEN
        val gifList = emptyList<Gif>()
        // WHEN
        val result = mapper.mapGifListToUiData(gifList, "")
        //THEN
        result.size.shouldBeEqualTo(1)
        result[0].shouldBeInstanceOf(EmptyItem::class.java)
        (result[0] as EmptyItem).title.shouldBeEqualTo(context.getString(R.string.empty_gif_list))
        stopKoin()
    }

    @Test
    fun mapGifListToUiData_searchNoResult() {
        // GIVEN
        val gifList = emptyList<Gif>()
        // WHEN
        val result = mapper.mapGifListToUiData(gifList, "cat")
        //THEN
        result.size.shouldBeEqualTo(1)
        result[0].shouldBeInstanceOf(EmptyItem::class.java)
        (result[0] as EmptyItem).title.shouldBeEqualTo(context.getString(R.string.search_list_no_result))
        stopKoin()
    }

    @Test
    fun mapGifListToUiData() {
        // GIVEN
        val gifList = listOf(CommonMockData.gif)
        // WHEN
        val result = mapper.mapGifListToUiData(gifList, "cat")
        //THEN
        result.size.shouldBeEqualTo(1)
        result[0].shouldBeInstanceOf(GifUiItem::class.java)
        (result[0] as GifUiItem).title.shouldBeEqualTo(CommonMockData.TITLE)
        (result[0] as GifUiItem).iconUrl.shouldBeEqualTo(CommonMockData.URL)
        stopKoin()
    }
}