package com.arctouch.codechallenge.home.presentation

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
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    override val layoutResId = R.layout.home_activity
    private lateinit var viewModel: HomeViewModel
    private val homeAdapter by lazy { HomeAdapter() }

    override fun onInit() {
        viewModel = provideViewModel(viewModelFactory) {
            observe(moviesResponseState, ::onMoviesResponse)
            observe(appException, ::onResponseError)
            getUpcomingMovies()
        }
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
}
