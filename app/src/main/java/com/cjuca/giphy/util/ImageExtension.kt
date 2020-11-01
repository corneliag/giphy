package com.cjuca.giphy.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cjuca.giphy.R

fun ImageView.loadImage(url: String?, @DrawableRes placeholder: Int) {
    if (url == null || url.isEmpty()) {
        setImageResource(placeholder)
    } else {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_connection_error)
            )
            .into(this)
    }
}