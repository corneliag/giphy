package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IGiphyApi {

    @GET("/v1/gifs/random")
    fun getRandomGif(@Query("api_key") key: String, @Query("rating") rating: String): Single<Gif>
}