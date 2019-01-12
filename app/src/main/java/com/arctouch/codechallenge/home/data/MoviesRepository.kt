package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.base.common.network.NetworkHandler
import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.base.data.BaseRemoteRepository
import com.arctouch.codechallenge.home.model.Movie
import com.arctouch.codechallenge.manager.data.QueryParams
import io.reactivex.Single
import javax.inject.Inject

interface MoviesRepository {

    fun upcomingMovies(queryParams: QueryParams): Single<BasePagination<Movie>>

    fun searchMovies(queryParams: QueryParams): Single<BasePagination<Movie>>

    class Remote @Inject constructor(
        private val moviesService: MoviesService,
        private val moviesMapper: MoviesMapper,
        networkHandler: NetworkHandler
    ) : BaseRemoteRepository(networkHandler), MoviesRepository {


        override fun upcomingMovies(queryParams: QueryParams): Single<BasePagination<Movie>> =
            request(moviesMapper) {
                moviesService.upcomingMovies(queryParams = queryParams.queryMap)
            }

        override fun searchMovies(queryParams: QueryParams): Single<BasePagination<Movie>> =
            request(moviesMapper) {
                moviesService.searchMovies(queryParams.queryMap)
            }
    }

}