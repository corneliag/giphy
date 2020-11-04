package com.cjuca.giphy

import com.cjuca.giphy.service.model.*

object CommonMockData {

    const val KEY = "9vyYOxGIq7nXJQGP4P0tHNjpN1i4ooWo"
    const val RATING = "g"

    const val URL = "https://media2.giphy.com/media/1111.gif"
    const val TITLE = "cat mountain GIF by Cheezburger"
    const val SEARCH_RESULTS_LIMIT = 10

    val gif = Gif(
        "fXgKfzV4aaHQI",
        URL,
        TITLE,
        images = GifType(
            fixed_width = GifImage(URL, "200", "300"),
            fixed_height = GifImage(URL, "200", "300")
        )
    )

    val meta = Meta(status = 200, msg = "OK", response_id = "1")
    val randomGifResponse = RandomGifResponse(
        data = gif,
        meta = meta
    )

    val searchGifResponse =
        SearchGifResponse(data = listOf(gif), meta = meta, pagination = Pagination(1500, 10, 0))
}