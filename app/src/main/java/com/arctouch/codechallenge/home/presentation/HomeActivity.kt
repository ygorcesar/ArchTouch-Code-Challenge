package com.arctouch.codechallenge.home.presentation

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.data.getPaginationItems
import com.arctouch.codechallenge.base.extensions.isVisible
import com.arctouch.codechallenge.base.extensions.observe
import com.arctouch.codechallenge.base.extensions.provideViewModel
import com.arctouch.codechallenge.base.presentation.BaseActivity
import com.arctouch.codechallenge.home.model.Movie
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*
import timber.log.Timber

class HomeActivity : BaseActivity() {

    override val layoutResId = R.layout.home_activity
    lateinit var viewModel: HomeViewModel

    override fun onInit() {
        viewModel = provideViewModel(viewModelFactory) {
            observe(moviesResponseState, ::onMoviesResponse)
            observe(appException, ::onResponseError)
            Timber.i("TESTE VIEW MODEL!!!!!")
        }
        viewModel.getUpcomingMovies()

//        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                val moviesWithGenres = it.results.map { movie ->
//                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
//                }
//                recyclerView.adapter = HomeAdapter(moviesWithGenres)
//                progressBar.visibility = View.GONE
//            }
    }

    private fun onMoviesResponse(viewState: ViewState?) {
        when (viewState) {
            ViewState.Loading -> loading(true)
            is ViewState.Complete<*> -> {
                showResults(viewState.getPaginationItems())
            }
            else -> loading(false)
        }
    }

    private fun onResponseError(appException: AppException?) {
        checkResponseException(appException) {}
    }

    private fun showResults(movies: List<Movie>) {
        loading(false)
        recyclerView?.adapter = HomeAdapter(movies)
    }

    override fun loading(isLoading: Boolean) {
        super.loading(isLoading)
        progressBar.isVisible = isLoading
    }
}
