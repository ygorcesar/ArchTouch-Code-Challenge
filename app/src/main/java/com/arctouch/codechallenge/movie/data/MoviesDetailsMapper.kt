package com.arctouch.codechallenge.movie.data

import com.arctouch.codechallenge.base.data.BaseMapper
import com.arctouch.codechallenge.home.model.Genre
import com.arctouch.codechallenge.manager.LoggerManager
import com.arctouch.codechallenge.manager.MovieImageUrlBuilder
import com.arctouch.codechallenge.movie.model.MovieDetails
import javax.inject.Inject

class MoviesDetailsMapper @Inject constructor(
    private val movieImageUrlBuilder: MovieImageUrlBuilder
) : BaseMapper<MovieDetailsRawResponse, MovieDetails>() {

    override val trackException: (Throwable) -> Unit = { LoggerManager.trackException(it) }

    override fun assertEssentialParams(raw: MovieDetailsRawResponse) {
        with(raw) {
            if (id == null) addMissingParam("movieId")

            if (title.isNullOrBlank()) addMissingParam("movieTitle")

            if (overview == null) addMissingParam("movieOverview")

            if (runtime == null) addMissingParam("movieRuntime")

            genres?.forEach { genre ->
                if (genre.id == null) addMissingParam("movieGenderId")
                if (genre.name == null) addMissingParam("movieGenderName")
            }
        }
    }

    override fun copyValues(raw: MovieDetailsRawResponse): MovieDetails {
        return MovieDetails(
            id = raw.id!!,
            title = raw.title!!,
            overview = raw.overview!!,
            genres = raw.genres?.map { genre -> Genre(genre.id!!, genre.name!!) } ?: listOf(),
            posterPath = movieImageUrlBuilder.buildPosterUrl(raw.posterPath ?: ""),
            backdropPath = movieImageUrlBuilder.buildBackdropUrl(raw.backdropPath ?: ""),
            releaseDate = raw.releaseDate ?: "",
            durationHour = raw.runtime!! / 60,
            durationMinutes = raw.runtime % 60,
            rating = raw.rating ?: 0.0
        )
    }

}