package com.arctouch.codechallenge.movie.domain

import com.arctouch.codechallenge.movie.data.MoviesRepository
import com.arctouch.codechallenge.movie.model.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailsInteractor @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    fun movieDetails(movieId: Long?): Single<MovieDetails> {
        return when (movieId) {
            null -> Single.error(MovieDetailsBusiness.MovieIdInvalid)
            -1L -> Single.error(MovieDetailsBusiness.MovieIdInvalid)
            else -> moviesRepository.movieDetails(movieId)
        }
    }
}