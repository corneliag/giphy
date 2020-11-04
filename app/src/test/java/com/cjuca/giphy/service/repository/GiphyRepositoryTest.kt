package com.cjuca.giphy.service.repository

import com.cjuca.giphy.CommonMockData
import com.cjuca.giphy.service.api.IGiphyClient
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import java.util.*

class GiphyRepositoryTest {

    private val repository: IGiphyRepository
    private val apiClient = mock<IGiphyClient>()

    init {
        repository = GiphyRepository(apiClient)
    }

    @Test
    fun getRandomGif() {
        whenever(apiClient.getRandomGif(CommonMockData.KEY, CommonMockData.RATING)).thenReturn(
            Single.just(CommonMockData.randomGifResponse)
        )

        repository.getRandomGif(CommonMockData.RATING)
            .test()
            .assertResult(CommonMockData.gif)

        verify(apiClient).getRandomGif(CommonMockData.KEY, CommonMockData.RATING)
        verifyNoMoreInteractions(apiClient)
    }


    @Test
    fun searchGifByKeyword() {
        val query = "home"
        val offset = 0
        whenever(
            apiClient.searchGifs(
                CommonMockData.KEY,
                CommonMockData.RATING,
                query,
                CommonMockData.SEARCH_RESULTS_LIMIT,
                Locale.getDefault().language,
                offset
            )
        ).thenReturn(
            Single.just(CommonMockData.searchGifResponse)
        )

        repository.searchGifs(
            CommonMockData.RATING,
            query,
            CommonMockData.SEARCH_RESULTS_LIMIT,
            offset
        )
            .test()
            .assertResult(CommonMockData.searchGifResponse)

        verify(apiClient).searchGifs(
            CommonMockData.KEY,
            CommonMockData.RATING,
            query,
            CommonMockData.SEARCH_RESULTS_LIMIT,
            Locale.getDefault().language,
            offset
        )
        verifyNoMoreInteractions(apiClient)
    }
}