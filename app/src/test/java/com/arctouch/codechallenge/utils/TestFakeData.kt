package com.arctouch.codechallenge.utils

import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.home.data.GenreRaw
import com.arctouch.codechallenge.home.data.GenreRawResponse
import com.arctouch.codechallenge.home.model.Genre
import com.arctouch.codechallenge.movie.model.Movie
import com.arctouch.codechallenge.movie.model.MovieDetails

object TestFakeData {

    val genre: Genre = Genre(1, "Ação")

    val genres: List<Genre> = listOf(genre)

    val genreRaw: GenreRaw = GenreRaw(1, "Ação")

    val genresRawResponse: GenreRawResponse = GenreRawResponse(listOf(genreRaw))

    val movie: Movie = Movie(
        1,
        "Batman",
        "O cavaleiro das trevas ressurge =)",
        genres,
        "poster_path",
        "backdrop_path",
        "06/12/2012"
    )

    val movies: List<Movie> = listOf(movie, movie, movie)

    val paginationMovies: BasePagination<Movie> = BasePagination(
        1,
        movies,
        2,
        20
    )

    val movieDetails: MovieDetails = MovieDetails(
        1,
        "Batman",
        "O cavaleiro das trevas ressurge =)",
        genres,
        "poster_path",
        "backdrop_path",
        "06/12/2012",
        1,
        32,
        8.5
    )

}