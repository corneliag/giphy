package com.cjuca.giphy.service.repository

import com.cjuca.giphy.BuildConfig
import com.cjuca.giphy.service.api.IGiphyClient
import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.SearchGifResponse
import io.reactivex.Single
import java.util.*

class GiphyRepository(private val apiClient: IGiphyClient) : IGiphyRepository {

    override fun getRandomGif(rating: String): Single<Gif> {
        return apiClient.getRandomGif(BuildConfig.GIPHY_API_KEY, rating).flatMap {
            Single.just(it.data)
        }
    }

    override fun searchGifs(
        rating: String,
        query: String,
        limit: Int,
        offset: Int
    ): Single<SearchGifResponse> {
        return apiClient.searchGifs(
            BuildConfig.GIPHY_API_KEY,
            rating,
            query,
            limit,
            Locale.getDefault().language,
            offset
        )
    }
}