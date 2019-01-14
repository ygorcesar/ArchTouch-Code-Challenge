package com.arctouch.codechallenge.manager

import android.content.Context
import com.arctouch.codechallenge.movie.presentation.MovieDetailsActivityArgs

object NavigationManager {

    fun goToMovieDetails(context: Context?, movieId: Long) {
        MovieDetailsActivityArgs(movieId).launch(context)
    }
}