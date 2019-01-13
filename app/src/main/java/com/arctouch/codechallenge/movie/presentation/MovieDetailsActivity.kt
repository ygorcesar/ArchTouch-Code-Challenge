package com.arctouch.codechallenge.movie.presentation

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.data.getResponse
import com.arctouch.codechallenge.base.extensions.*
import com.arctouch.codechallenge.base.presentation.BaseActivity
import com.arctouch.codechallenge.base.presentation.views.ErrorView
import com.arctouch.codechallenge.movie.domain.MovieDetailsBusiness
import com.arctouch.codechallenge.movie.model.MovieDetails
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.movie_details_activity.*

class MovieDetailsActivity : BaseActivity() {

    override val layoutResId = R.layout.movie_details_activity
    override val baseErrorView: ErrorView?
        get() = errorView.apply { setActionButtonClick { viewModel.getMovieDetails(args.movieId) } }

    private lateinit var viewModel: MovieDetailsViewModel
    private val args by lazy { MovieDetailsActivityArgs.deserializeFrom(intent) }

    override fun onInit() {
        viewModel = provideViewModel(viewModelFactory) {
            observe(movieDetailsResponseState, ::onMovieDetailsResponse)
            observe(appException, ::onResponseError)
            getMovieDetails(args.movieId)
        }
        movieDetailsBackButton?.setOnClickListener { finish() }
    }

    private fun onMovieDetailsResponse(viewState: ViewState?) {
        when (viewState) {
            ViewState.Loading -> loading(true)
            is ViewState.Complete<*> -> showMovieDetails(viewState.getResponse())
            else -> loading(false)
        }
    }

    private fun onResponseError(appException: AppException?) {
        checkResponseException(appException) { exception ->
            when (exception) {
                MovieDetailsBusiness.MovieIdInvalid -> errorView?.showError(subTitle = R.string.movie_details_validations_invalid_movie_id)
            }
        }
    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        movieDetailsImage.loadImage(movieDetails.posterPath)
        movieDetailsTitle.text = movieDetails.title
        movieDetailsOverview.text = movieDetails.overview
        movieDetailsRating.text = movieDetails.rating.toString()
        movieDetailsDuration.text = getMovieDuration(movieDetails)
        movieDetails.genres.forEach { genre ->
            val chip = Chip(this).apply {
                setChipBackgroundColorResource(R.color.colorAccent)
                setTextColor(getColorRes(android.R.color.white))
                text = genre.name
            }
            movieDetailsGenres.addView(chip)
        }

        loading(false)
    }

    private fun getMovieDuration(movieDetails: MovieDetails): String {
        val durationHour = if (movieDetails.durationHour > 0)
            resources.getQuantityString(
                R.plurals.movie_details_duration_hour,
                movieDetails.durationHour,
                movieDetails.durationHour
            ) else ""

        val durationMinutes = if (movieDetails.durationMinutes > 0)
            getString(
                R.string.movie_details_duration_minutes,
                movieDetails.durationMinutes
            ) else ""

        return if (durationHour.isNotBlank() && durationMinutes.isNotBlank()) {
            getString(
                R.string.movie_details_duration_hour_and_minutes,
                durationHour,
                durationMinutes
            )
        } else "$durationHour$durationMinutes"
    }

    override fun loading(isLoading: Boolean) {
        movieDetailsProgress.isVisible = isLoading
    }
}