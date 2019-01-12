package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.application.di.modules.NetworkModule
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = NetworkModule.DEFAULT_LANGUAGE
    ): Single<GenreRawResponse>

}