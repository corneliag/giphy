package com.cjuca.giphy.service.model

data class Gif(
    val id: String,
    val url: String,
    val title: String,
    val images: GifType
)