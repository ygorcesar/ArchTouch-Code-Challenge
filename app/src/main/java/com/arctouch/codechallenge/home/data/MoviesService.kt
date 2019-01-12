package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.application.di.modules.NetworkModule
import com.arctouch.codechallenge.base.data.BasePaginationRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = NetworkModule.DEFAULT_LANGUAGE,
        @Query("page") page: Long,
        @Query("region") region: String = NetworkModule.DEFAULT_REGION
    ): Single<BasePaginationRaw<MovieRawResponse>>

}