package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.RandomGifResponse
import com.cjuca.giphy.service.model.SearchGifResponse
import io.reactivex.Single
import retrofit2.http.Query

interface IGiphyClient {

    /**
     * @param key api key use your own
     * @param key rating use level 1 for commonly witnessed by people
     */
    fun getRandomGif(key: String, rating: String): Single<RandomGifResponse>

    /**
     * @param key api key use your own
     * @param key rating use level 1 for commonly witnessed by people
     * @param key q for the search query
     * @param key limit for the maximum number of objects to return
     * @param key lang for the language
     * @param key offset for the Position in pagination
     */
    fun searchGifs(
        @Query("api_key") key: String,
        @Query("rating") rating: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("lang") language: String,
        @Query("offset") offset: Int
    ): Single<SearchGifResponse>
}