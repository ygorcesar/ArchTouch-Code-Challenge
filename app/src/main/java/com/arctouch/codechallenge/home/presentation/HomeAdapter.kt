package com.arctouch.codechallenge.home.presentation

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.extensions.loadImage
import com.arctouch.codechallenge.base.presentation.BaseRecyclerViewAdapter
import com.arctouch.codechallenge.home.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter(movies: List<Movie>) : BaseRecyclerViewAdapter<Movie>(
    items = movies.toMutableList(),
    layoutResId = R.layout.movie_item,
    bindView = { view, movie ->

        view.titleTextView.text = movie.title
        view.genresTextView.text = movie.genres.joinToString(separator = ", ") { it.name }
        view.releaseDateTextView.text = movie.releaseDate

        view.posterImageView.loadImage(movie.posterPath)
    })