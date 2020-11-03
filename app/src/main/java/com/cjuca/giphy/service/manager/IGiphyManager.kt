package com.cjuca.giphy.service.manager

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.SearchGifResponse
import io.reactivex.Single
import retrofit2.http.Query

interface IGiphyManager {

    fun getRandomGif(rating: String): Single<Gif>

    fun searchGifs(
        @Query("rating") rating: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<SearchGifResponse>
}