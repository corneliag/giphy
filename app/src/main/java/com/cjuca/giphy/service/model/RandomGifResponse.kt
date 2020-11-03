package com.cjuca.giphy.service.model

data class RandomGifResponse(
    val data: Gif,
    val pagination: Pagination? = null,
    val meta: Meta
)