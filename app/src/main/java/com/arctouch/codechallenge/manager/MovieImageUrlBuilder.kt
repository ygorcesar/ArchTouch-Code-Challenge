package com.arctouch.codechallenge.manager

import com.arctouch.codechallenge.application.di.modules.NetworkModule
import javax.inject.Inject
import javax.inject.Named

private val POSTER_URL = "https://image.tmdb.org/t/p/w154"
private val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

class MovieImageUrlBuilder @Inject constructor(
    @Named(NetworkModule.API_KEY_NAMED) private val apiKey: String
) {

    fun buildPosterUrl(posterPath: String): String = "$POSTER_URL$posterPath?api_key=$apiKey"

    fun buildBackdropUrl(backdropPath: String): String =
        "$BACKDROP_URL$backdropPath?api_key=$apiKey"

}
