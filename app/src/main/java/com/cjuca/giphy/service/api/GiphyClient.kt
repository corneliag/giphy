package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.Rating
import io.reactivex.Single

class GiphyClient(private val api: IGiphyApi) : IGiphyClient {

    override fun getRandomGif(key: String, rating: Rating): Single<Gif> {
        return api.getRandomGif(key, rating)
    }
}