package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.RandomGifResponse
import com.cjuca.giphy.service.model.SearchGifResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IGiphyApi {

    @GET("/v1/gifs/random")
    fun getRandomGif(
        @Query("api_key") key: String,
        @Query("rating") rating: String
    ): Single<RandomGifResponse>

    @GET("/v1/gifs/search")
    fun searchGifs(
        @Query("api_key") key: String,
        @Query("rating") rating: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("lang") language: String,
        @Query("offset") offset: Int
    ): Single<SearchGifResponse>
}