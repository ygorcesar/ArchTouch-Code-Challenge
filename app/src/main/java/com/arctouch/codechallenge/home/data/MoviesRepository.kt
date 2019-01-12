package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.application.di.modules.NetworkModule
import com.arctouch.codechallenge.base.common.network.NetworkHandler
import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.base.data.BaseRemoteRepository
import com.arctouch.codechallenge.home.model.Movie
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

interface MoviesRepository {

    fun upcomingMovies(page: Long): Single<BasePagination<Movie>>


    class Remote @Inject constructor(
        private val moviesService: MoviesService,
        private val moviesMapper: MoviesMapper,
        @Named(NetworkModule.API_KEY_NAMED) private val apiKey: String,
        networkHandler: NetworkHandler
    ) : BaseRemoteRepository(networkHandler), MoviesRepository {


        override fun upcomingMovies(page: Long): Single<BasePagination<Movie>> =
            request(moviesMapper) {
                moviesService.upcomingMovies(apiKey = apiKey, page = page)
            }
    }

}