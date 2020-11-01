package com.cjuca.giphy.service.manager

import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single

interface IGiphyManager {

    fun getRandomGif(rating: String): Single<Gif>
}