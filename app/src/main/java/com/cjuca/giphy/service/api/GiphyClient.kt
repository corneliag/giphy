package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.RandomGifResponse
import com.cjuca.giphy.service.model.SearchGifResponse
import io.reactivex.Single

class GiphyClient(private val api: IGiphyApi) : IGiphyClient {

    override fun getRandomGif(key: String, rating: String): Single<RandomGifResponse> {
        return api.getRandomGif(key, rating)
    }

    override fun searchGifs(
        key: String,
        rating: String,
        query: String,
        limit: Int,
        language: String,
        offset: Int
    ): Single<SearchGifResponse> {
        return api.searchGifs(key, rating, query, limit, language, offset)
    }
}