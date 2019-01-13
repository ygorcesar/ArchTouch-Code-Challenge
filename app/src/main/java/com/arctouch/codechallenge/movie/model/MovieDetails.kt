package com.arctouch.codechallenge.movie.model

import com.arctouch.codechallenge.home.model.Genre

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val durationHour: Int,
    val durationMinutes: Int,
    val rating: Double
)
