package com.arctouch.codechallenge.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.extensions.addToComposite
import com.arctouch.codechallenge.base.extensions.observeOnComputation
import com.arctouch.codechallenge.base.presentation.BaseViewModel
import com.arctouch.codechallenge.movie.domain.MovieDetailsInteractor
import com.arctouch.codechallenge.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsInteractor: MovieDetailsInteractor
) : BaseViewModel() {

    val movieDetailsResponseState = MutableLiveData<ViewState>()

    fun getMovieDetails(movieId: Long?) {
        compositeDisposable.clear()

        movieDetailsInteractor.movieDetails(movieId)
            .observeOnComputation()
            .doOnSubscribe { movieDetailsResponseState.postValue(ViewState.Loading) }
            .subscribe({ movieDetails ->
                movieDetailsResponseState.postValue(ViewState.Complete(movieDetails))
            }, { error -> handleFailure(error) })
            .addToComposite(compositeDisposable)
    }
}