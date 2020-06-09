package com.abrahamlay.base.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object GlideHelper {
    fun showImage(url: String, imageView: ImageView, context: Context) {
        val options = RequestOptions()
            .centerCrop()

        val requestBuilder = Glide.with(context)
            .load(url)

        requestBuilder
            .apply(options)
            .into(imageView)
    }
}