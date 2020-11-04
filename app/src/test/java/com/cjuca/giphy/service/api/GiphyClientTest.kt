package com.cjuca.giphy.service.api

import com.cjuca.giphy.CommonMockData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import java.io.IOException

class GiphyClientTest {
    private val apiClient: IGiphyClient
    private val api = mock<IGiphyApi>()

    init {
        apiClient = GiphyClient(api)
    }

    @Test
    fun getRandomGif() {
        whenever(api.getRandomGif(CommonMockData.KEY, CommonMockData.RATING)).thenReturn(
            Single.just(
                CommonMockData.randomGifResponse
            )
        )

        apiClient.getRandomGif(CommonMockData.KEY, CommonMockData.RATING)
            .test()
            .assertNoErrors()
            .assertValue(CommonMockData.randomGifResponse)
            .dispose()

        verify(api).getRandomGif(CommonMockData.KEY, CommonMockData.RATING)
        verifyNoMoreInteractions(api)
    }

    @Test

    fun getRandomGif_error() {
        whenever(
            api.getRandomGif(
                CommonMockData.KEY,
                CommonMockData.RATING
            )
        ).thenReturn(Single.error(IOException()))

        apiClient.getRandomGif(CommonMockData.KEY, CommonMockData.RATING)
            .test().assertSubscribed()
            .assertNotComplete().dispose()

        verify(api).getRandomGif(CommonMockData.KEY, CommonMockData.RATING)
        verifyNoMoreInteractions(api)
    }

}