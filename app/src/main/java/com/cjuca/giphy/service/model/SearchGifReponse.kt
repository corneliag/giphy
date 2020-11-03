package com.cjuca.giphy.service.model

data class SearchGifResponse(
    val data: List<Gif>,
    val pagination: Pagination? = null,
    val meta: Meta
)