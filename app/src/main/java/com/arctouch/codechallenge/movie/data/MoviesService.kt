package com.arctouch.codechallenge.movie.data

import com.arctouch.codechallenge.base.data.BasePaginationRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MoviesService {

    @GET("movie/upcoming")
    fun upcomingMovies(
        @QueryMap queryParams: Map<String, String>? = null
    ): Single<BasePaginationRaw<MovieRawResponse>>

    @GET("search/movie")
    fun searchMovies(
        @QueryMap queryParams: Map<String, String>? = null
    ): Single<BasePaginationRaw<MovieRawResponse>>

    @GET("movie/{id}")
    fun movie(
        @Path("id") id: Long,
        @QueryMap queryParams: Map<String, String>? = null
    ): Single<MovieDetailsRawResponse>
}