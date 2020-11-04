package com.cjuca.giphy.service.manager

import com.cjuca.giphy.CommonMockData
import com.cjuca.giphy.service.repository.IGiphyRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GiphyManagerTest {

    private val manager: IGiphyManager
    private val repository = mock<IGiphyRepository>()

    init {
        manager = GiphyManager(repository)
    }

    @Test
    fun getRandomGif() {
        whenever(repository.getRandomGif(CommonMockData.RATING)).thenReturn(
            Single.just(CommonMockData.gif)
        )

        manager.getRandomGif(CommonMockData.RATING)
            .test()
            .assertNoErrors()
            .assertResult(CommonMockData.gif)

        verify(repository).getRandomGif(CommonMockData.RATING)
        verifyNoMoreInteractions(repository)
    }
}