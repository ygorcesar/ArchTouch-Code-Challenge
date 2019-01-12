package com.arctouch.codechallenge.base.extensions

import android.view.View

var View?.isVisible: Boolean
    get() = this?.visibility == View.VISIBLE
    set(isVisible) {
        this?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }