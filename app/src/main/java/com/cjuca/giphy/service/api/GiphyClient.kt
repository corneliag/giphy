package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single

class GiphyClient(private val api: IGiphyApi) : IGiphyClient {

    override fun getRandomGif(key: String, rating: String): Single<Gif> {
        return api.getRandomGif(key, rating)
    }
}