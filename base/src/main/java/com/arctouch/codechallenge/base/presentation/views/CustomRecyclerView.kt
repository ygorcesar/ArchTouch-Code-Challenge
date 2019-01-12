package com.arctouch.codechallenge.base.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.extensions.isVisible
import com.arctouch.codechallenge.base.extensions.setLinearLayout
import com.arctouch.codechallenge.base.presentation.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.custom_recycler_view.view.*

class CustomRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        val appearance = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView)
        val crvPaddingTop =
            appearance.getDimension(R.styleable.CustomRecyclerView_crv_padding_top, 0F).toInt()
        val crvPaddingLeft =
            appearance.getDimension(R.styleable.CustomRecyclerView_crv_padding_left, 0F).toInt()
        val crvPaddingRight =
            appearance.getDimension(R.styleable.CustomRecyclerView_crv_padding_right, 0F).toInt()
        val crvPaddingBottom =
            appearance.getDimension(R.styleable.CustomRecyclerView_crv_padding_bottom, 0F).toInt()

        View.inflate(context, R.layout.custom_recycler_view, this)

        customRecyclerView.setPadding(
            crvPaddingLeft,
            crvPaddingTop,
            crvPaddingRight,
            crvPaddingBottom
        )
        appearance.recycle()
    }

    fun <T> setAdapter(adapter: BaseRecyclerViewAdapter<T>) {
        this.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    var loading: Boolean = false
        set(loading) {
            field = loading
            if (loading) showProgress() else hideProgress()
        }

    var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?
        get() = customRecyclerView?.adapter
        set(adapter) {
            customRecyclerView?.adapter = adapter
        }

    fun setLinearLayout(orientation: Int = RecyclerView.VERTICAL, hasFixedSize: Boolean = true) {
        customRecyclerView?.setLinearLayout(orientation, hasFixedSize)
    }

    private fun showProgress() {
        progress.isVisible = true
    }

    private fun hideProgress() {
        progress.isVisible = false
    }

    fun addOnScrollListener(onScrollAtEnd: () -> Unit) {
        customRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(customRecyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(customRecyclerView, dx, dy)
                val layoutManager = customRecyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!loading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        onScrollAtEnd()
                    }
                }
            }
        })
    }
}