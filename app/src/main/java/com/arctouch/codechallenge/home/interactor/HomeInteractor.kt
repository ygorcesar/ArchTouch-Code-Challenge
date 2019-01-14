package com.arctouch.codechallenge.home.interactor

import com.arctouch.codechallenge.home.data.GenreRepository
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.movie.data.MoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val genreRepository: GenreRepository,
    private val moviesRepository: MoviesRepository
) {

    fun getGenres(): Completable = genreRepository.getGenres()

    fun upcomingMovies(queryParams: QueryParams) = moviesRepository.upcomingMovies(queryParams)

    fun searchMovies(queryParams: QueryParams) = moviesRepository.searchMovies(queryParams)
}