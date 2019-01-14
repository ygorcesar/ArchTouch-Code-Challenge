package com.arctouch.codechallenge.movie.presentation

import android.content.Context
import android.content.Intent
import com.arctouch.codechallenge.base.presentation.ActivityArgs

class MovieDetailsActivityArgs(
    val movieId: Long
) : ActivityArgs {

    override fun intent(context: Context) =
        Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(MOVIE_ID_KEY, movieId)
        }

    companion object {

        fun deserializeFrom(intent: Intent) = MovieDetailsActivityArgs(
            movieId = intent.getLongExtra(MOVIE_ID_KEY, -1L)
        )

        private const val MOVIE_ID_KEY = "movie_id_key"

    }
}