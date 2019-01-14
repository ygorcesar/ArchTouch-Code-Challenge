package com.arctouch.codechallenge.base.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.extensions.getString
import com.arctouch.codechallenge.base.extensions.isVisible
import kotlinx.android.synthetic.main.error_view.view.*

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.error_view, this)

        isFillViewport = true
        elevation = resources.getDimension(R.dimen.elevation_error_view)

    }

    fun showError(
        title: Int = R.string.action_error_ops,
        subTitle: Int = R.string.error_occurred_some_problem
    ) {
        errorViewTitle.text = getString(title)
        errorViewSubTitle.text = getString(subTitle)
        showView()
    }

    fun showError(subTitle: String) {
        errorViewTitle.text = getString(R.string.action_error_ops)
        errorViewSubTitle.text = subTitle
        showView()
    }

    private fun showView() {
        isVisible = true
    }

    private fun hideError() {
        isVisible = false
    }

    fun setActionButtonClick(onButtonClick: () -> Unit) {
        errorViewButton.setOnClickListener {
            hideError()
            onButtonClick()
        }
    }

    fun showConnectionError() {
        errorViewTitle.text = getString(R.string.action_error_ops)
        errorViewSubTitle.text = getString(R.string.error_no_internet_connection_subtitle)
        isVisible = true
    }

}