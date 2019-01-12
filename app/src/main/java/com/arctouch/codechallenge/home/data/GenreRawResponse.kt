package com.arctouch.codechallenge.home.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreRawResponse(
    @Json(name = "genres") val genres: List<GenreRaw>?
)

@JsonClass(generateAdapter = true)
data class GenreRaw(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?
)