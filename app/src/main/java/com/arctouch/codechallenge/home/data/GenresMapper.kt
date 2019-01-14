package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.base.data.BaseMapper
import com.arctouch.codechallenge.home.model.Genre
import com.arctouch.codechallenge.manager.LoggerManager
import javax.inject.Inject

class GenresMapper @Inject constructor() : BaseMapper<GenreRawResponse, List<Genre>>() {

    override val trackException: (Throwable) -> Unit = { LoggerManager.trackException(it) }

    override fun assertEssentialParams(raw: GenreRawResponse) {
        raw.genres?.forEach { genreRaw ->
            if (genreRaw.id == null) addMissingParam("genreId")
            if (genreRaw.name.isNullOrBlank()) addMissingParam("genreName")
        }
    }

    override fun copyValues(raw: GenreRawResponse): List<Genre> {
        return raw.genres?.map { genreRaw ->
            Genre(
                id = genreRaw.id!!,
                name = genreRaw.name!!
            )
        } ?: listOf()
    }
}