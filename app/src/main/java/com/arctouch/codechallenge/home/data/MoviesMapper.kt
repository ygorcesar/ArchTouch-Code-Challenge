package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.base.data.BasePaginationMapper
import com.arctouch.codechallenge.home.model.Movie
import com.arctouch.codechallenge.manager.LoggerManager
import com.arctouch.codechallenge.manager.MovieImageUrlBuilder
import com.arctouch.codechallenge.manager.session.SessionManager
import javax.inject.Inject

class MoviesMapper @Inject constructor(
    private val sessionManager: SessionManager,
    private val movieImageUrlBuilder: MovieImageUrlBuilder
) : BasePaginationMapper<MovieRawResponse, Movie>() {

    private val genres by lazy { sessionManager.genres }

    override val trackException: (Throwable) -> Unit = { LoggerManager.trackException(it) }

    override fun assertPaginationEssentialParams(pageRawItem: MovieRawResponse) {
        with(pageRawItem) {

            if (id == null) addMissingParam("movieId")

            if (title.isNullOrBlank()) addMissingParam("movieTitle")

            if (overview == null) addMissingParam("movieOverview")
        }
    }

    override fun copyPaginationRawToModel(rawItem: MovieRawResponse): Movie {
        return Movie(
            id = rawItem.id!!,
            title = rawItem.title!!,
            overview = rawItem.overview!!,
            genres = genres.filter { genre -> rawItem.genreIds?.contains(genre.id) == true },
            posterPath = movieImageUrlBuilder.buildPosterUrl(rawItem.posterPath ?: ""),
            backdropPath = movieImageUrlBuilder.buildBackdropUrl(rawItem.backdropPath ?: ""),
            releaseDate = rawItem.releaseDate ?: ""
        )
    }

}