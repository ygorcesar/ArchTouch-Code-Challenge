package com.arctouch.codechallenge.base.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasePaginationRaw<T>(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<T>?,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)