package com.cjuca.giphy.service.repository

import com.cjuca.giphy.service.model.Gif
import io.reactivex.Single

interface IGiphyRepository {

    fun getRandomGif(rating: String): Single<Gif>
}