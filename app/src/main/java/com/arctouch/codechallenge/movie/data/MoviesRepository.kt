package com.arctouch.codechallenge.movie.data

import com.arctouch.codechallenge.base.common.network.NetworkHandler
import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.base.data.BaseRemoteRepository
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.movie.model.Movie
import com.arctouch.codechallenge.movie.model.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

interface MoviesRepository {

    fun upcomingMovies(queryParams: QueryParams): Single<BasePagination<Movie>>

    fun searchMovies(queryParams: QueryParams): Single<BasePagination<Movie>>

    fun movieDetails(movieId: Long): Single<MovieDetails>

    class Remote @Inject constructor(
        private val moviesService: MoviesService,
        private val moviesMapper: MoviesMapper,
        private val moviesDetailsMapper: MoviesDetailsMapper,
        private val queryParams: QueryParams,
        networkHandler: NetworkHandler
    ) : BaseRemoteRepository(networkHandler),
        MoviesRepository {

        override fun upcomingMovies(queryParams: QueryParams): Single<BasePagination<Movie>> =
            request(moviesMapper) {
                moviesService.upcomingMovies(queryParams = queryParams.queryMap)
            }

        override fun searchMovies(queryParams: QueryParams): Single<BasePagination<Movie>> =
            request(moviesMapper) {
                moviesService.searchMovies(queryParams.queryMap)
            }

        override fun movieDetails(movieId: Long): Single<MovieDetails> =
            request(moviesDetailsMapper) {
                moviesService.movie(movieId, queryParams.queryMap)
            }
    }

}