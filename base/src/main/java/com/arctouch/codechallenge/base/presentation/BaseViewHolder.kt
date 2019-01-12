package com.arctouch.codechallenge.base.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T>(val view: View, private val executeBinding: (view: View, item: T) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bindView(item: T) {
        this.executeBinding(view, item)
    }
}