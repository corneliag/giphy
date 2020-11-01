package com.cjuca.giphy.service.repository

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.Rating
import io.reactivex.Single

interface IGiphyRepository {

    fun getRandomGif(rating: Rating): Single<Gif>
}