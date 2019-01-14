package com.arctouch.codechallenge.base.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.extensions.isVisible
import kotlinx.android.synthetic.main.skeleton_view.view.*

class SkeletonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        val appearance = context.obtainStyledAttributes(attrs, R.styleable.SkeletonView)
        val skeletonLayoutId =
            appearance.getResourceId(R.styleable.SkeletonView_skeleton_layout, -1)
        View.inflate(context, R.layout.skeleton_view, this)

        elevation = resources.getDimension(R.dimen.elevation_error_view)

        if (skeletonLayoutId != -1) {
            val skeletonLayout = View.inflate(context, skeletonLayoutId, null)
            shimmer.addView(skeletonLayout)
        }
        appearance.recycle()
    }

    fun show() {
        isVisible = true
        shimmer.isVisible = true
        shimmer.startShimmer()
    }

    fun hide() {
        shimmer.stopShimmer()
        shimmer.isVisible = false
        isVisible = false
    }

}