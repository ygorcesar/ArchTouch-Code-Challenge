package com.arctouch.codechallenge.home.presentation

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.extensions.dateFromUsToBr
import com.arctouch.codechallenge.base.extensions.getColorRes
import com.arctouch.codechallenge.base.extensions.loadImage
import com.arctouch.codechallenge.base.presentation.BaseRecyclerViewAdapter
import com.arctouch.codechallenge.home.model.Movie
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter : BaseRecyclerViewAdapter<Movie>(
    layoutResId = R.layout.movie_item,
    bindView = { view, movie ->

        view.titleTextView.text = movie.title
        view.releaseDateTextView.text = movie.releaseDate.dateFromUsToBr()

        view.genres.removeAllViews()
        movie.genres.forEach { genre ->
            val chip = Chip(view.context).apply {
                setChipBackgroundColorResource(R.color.colorPrimary)
                setTextColor(view.context.getColorRes(android.R.color.white))
                text = genre.name
            }
            view.genres.addView(chip)
        }

        view.posterImageView.loadImage(movie.posterPath)
    })