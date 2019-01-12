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
        @Query("region") region: String? = null //REMOVED {region=BR}/ Set NULL is default, because currently is getting only 6 items and not have more than one page when BR is setted
    ): Single<BasePaginationRaw<MovieRawResponse>>

}