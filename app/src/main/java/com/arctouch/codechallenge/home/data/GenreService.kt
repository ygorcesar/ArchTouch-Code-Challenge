package com.arctouch.codechallenge.home.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GenreService {

    @GET("genre/movie/list")
    fun genres(
        @QueryMap queryParams: Map<String, String>? = null
    ): Single<GenreRawResponse>

}