package com.cjuca.giphy.service.manager

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.Rating
import io.reactivex.Single

interface IGiphyManager {

    fun getRandomGif(rating: Rating): Single<Gif>
}