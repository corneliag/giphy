package com.cjuca.giphy.service.api

import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single

interface IGiphyClient {

    /**
     * @param key api key use your own
     * @param key rating use level 1 for commonly witnessed by people
     */
    fun getRandomGif(key: String, rating: String): Single<Gif>
}