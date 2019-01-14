package com.arctouch.codechallenge.base.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.extensions.getString
import com.arctouch.codechallenge.base.extensions.isVisible
import kotlinx.android.synthetic.main.empty_view.view.*

class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    init {

        View.inflate(context, R.layout.empty_view, this)

        isFillViewport = true
        elevation = resources.getDimension(R.dimen.elevation_error_view)

    }

    fun show(
        title: Int = R.string.action_error_ops,
        subTitle: Int = R.string.empty_view_no_information_found
    ) {
        emptyViewTitle.text = getString(title)
        emptyViewSubTitle.text = getString(subTitle)
        showView()
    }

    private fun showView() {
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }


}