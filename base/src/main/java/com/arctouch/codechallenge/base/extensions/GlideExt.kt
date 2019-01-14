package com.arctouch.codechallenge.base.extensions

import android.widget.ImageView
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.di.GlideApp

fun ImageView?.loadImage(
    url: String?,
    withAnimate: Boolean = true
) {
    this?.apply {
        GlideApp.with(this)
            .load(url)
            .error(R.drawable.ic_image_placeholder)
            .apply {
                if (!withAnimate) dontAnimate()
            }
            .into(this)
    }
}
