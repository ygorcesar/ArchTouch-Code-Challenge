package com.arctouch.codechallenge.home.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String
)
