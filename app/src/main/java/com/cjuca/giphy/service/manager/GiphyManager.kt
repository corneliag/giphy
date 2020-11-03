package com.cjuca.giphy.service.manager

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.SearchGifResponse
import com.cjuca.giphy.service.repository.IGiphyRepository
import io.reactivex.Single

class GiphyManager(private val repository: IGiphyRepository) : IGiphyManager {

    override fun getRandomGif(rating: String): Single<Gif> = repository.getRandomGif(rating)

    override fun searchGifs(
        rating: String,
        query: String,
        limit: Int,
        offset: Int
    ): Single<SearchGifResponse> {
        return repository.searchGifs(rating, query, limit, offset)
    }
}