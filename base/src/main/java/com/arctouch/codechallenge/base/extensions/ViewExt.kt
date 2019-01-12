package com.arctouch.codechallenge.base.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

var View?.isVisible: Boolean
    get() = this?.visibility == View.VISIBLE
    set(isVisible) {
        this?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


fun androidx.recyclerview.widget.RecyclerView?.setLinearLayout(
    orientation: Int = RecyclerView.VERTICAL,
    hasFixedSize: Boolean = true
) {
    this?.apply {
        setHasFixedSize(hasFixedSize)
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this.context,
            orientation,
            false
        )
    }
}