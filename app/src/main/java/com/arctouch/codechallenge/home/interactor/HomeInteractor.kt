package com.arctouch.codechallenge.home.interactor

import com.arctouch.codechallenge.home.data.GenreRepository
import com.arctouch.codechallenge.home.data.MoviesRepository
import com.arctouch.codechallenge.manager.data.QueryParams
import io.reactivex.Completable
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val genreRepository: GenreRepository,
    private val moviesRepository: MoviesRepository.Remote
) {

    fun getGenres(): Completable = genreRepository.getGenres()

    fun upcomingMovies(queryParams: QueryParams) = moviesRepository.upcomingMovies(queryParams)

    fun searchMovies(queryParams: QueryParams) = moviesRepository.searchMovies(queryParams)
}