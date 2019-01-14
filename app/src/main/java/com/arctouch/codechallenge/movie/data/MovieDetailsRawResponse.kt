package com.arctouch.codechallenge.movie.data

import com.arctouch.codechallenge.home.data.GenreRaw
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsRawResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "title") val title: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "genres") val genres: List<GenreRaw>?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "vote_average") val rating: Double?
)
