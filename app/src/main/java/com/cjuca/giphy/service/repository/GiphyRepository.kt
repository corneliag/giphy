package com.cjuca.giphy.service.repository

import com.cjuca.giphy.BuildConfig
import com.cjuca.giphy.service.api.IGiphyClient
import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single

class GiphyRepository(private val apiClient: IGiphyClient) : IGiphyRepository {

    override fun getRandomGif(rating: String): Single<Gif> {
        return apiClient.getRandomGif(BuildConfig.GIPHY_API_KEY, rating)
    }
}