package com.arctouch.codechallenge.home.presentation

import android.os.Bundle
import android.os.Parcelable
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.data.getList
import com.arctouch.codechallenge.base.extensions.isVisible
import com.arctouch.codechallenge.base.extensions.observe
import com.arctouch.codechallenge.base.extensions.provideViewModel
import com.arctouch.codechallenge.base.presentation.BaseActivity
import com.arctouch.codechallenge.home.model.Movie
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.custom_recycler_view.*
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    override val layoutResId = R.layout.home_activity
    private lateinit var viewModel: HomeViewModel
    private val homeAdapter by lazy { HomeAdapter() }
    private var savedRecyclerLayoutState: Parcelable? = null

    override fun onInit() {
        viewModel = provideViewModel(viewModelFactory) {
            observe(moviesResponseState, ::onMoviesResponse)
            observe(appException, ::onResponseError)
            getUpcomingMovies()
        }
    }

    override fun onResume() {
        super.onResume()
        savedRecyclerLayoutState?.let {
            customRecyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(
            KEY_RECYCLER_VIEW_STATE,
            customRecyclerView.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedRecyclerLayoutState = savedInstanceState?.getParcelable(KEY_RECYCLER_VIEW_STATE)
    }

    private fun onMoviesResponse(viewState: ViewState?) {
        when (viewState) {
            ViewState.Loading -> loading(true)
            is ViewState.Complete<*> -> {
                showResults(viewState.getList())
            }
            else -> loading(false)
        }
    }

    private fun onResponseError(appException: AppException?) {
        checkResponseException(appException) {}
    }

    private fun showResults(movies: List<Movie>) {
        loading(false)
        recyclerView?.apply {
            if (adapter == null) {
                setLinearLayout()
                setAdapter(homeAdapter)
                addOnScrollListener { viewModel.getUpcomingMovies(nextPage = true) }
            }
        }
        homeAdapter.addItems(movies)
    }

    override fun loading(isLoading: Boolean) {
        when {
            homeAdapter.getItems().isEmpty() -> progressBar.isVisible = isLoading
            else -> recyclerView?.loading = isLoading
        }
    }

    companion object {
        private const val KEY_RECYCLER_VIEW_STATE = "recycler_view_state"
    }
}
