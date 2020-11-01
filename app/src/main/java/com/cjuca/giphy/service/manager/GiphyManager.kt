package com.cjuca.giphy.service.manager

import com.cjuca.giphy.service.model.Gif
import com.cjuca.giphy.service.model.Rating
import com.cjuca.giphy.service.repository.IGiphyRepository
import io.reactivex.Single

class GiphyManager(private val repository: IGiphyRepository) : IGiphyManager {

    override fun getRandomGif(rating: Rating): Single<Gif> = repository.getRandomGif(rating)

}