package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.extensions.addToComposite
import com.arctouch.codechallenge.base.extensions.observeOnComputation
import com.arctouch.codechallenge.base.presentation.BaseViewModel
import com.arctouch.codechallenge.home.interactor.HomeInteractor
import com.arctouch.codechallenge.home.model.Movie
import io.reactivex.Completable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor
) : BaseViewModel() {

    val moviesResponseState = MutableLiveData<ViewState>()
    private var page: Long = 1L
    private var totalPages: Int = -1
    private val movies = mutableListOf<Movie>()

    fun getUpcomingMovies(nextPage: Boolean = false) {
        if (movies.isNotEmpty() && !nextPage) {
            moviesResponseState.postValue(ViewState.Complete(movies))
        } else if (hasNextPage()) {
            compositeDisposable.clear()

            val calls = Completable.concat(
                listOf(
                    homeInteractor.getGenres(),
                    homeInteractor.upcomingMovies(page)
                        .doOnSuccess { movies ->
                            totalPages = movies.totalPages
                            if (page <= movies.totalPages) page = page.inc()
                            this.movies.addAll(movies.results)
                            moviesResponseState.postValue(ViewState.Complete(movies.results))
                        }.ignoreElement()
                )
            )
            calls.observeOnComputation()
                .doOnSubscribe { moviesResponseState.postValue(ViewState.Loading) }
                .subscribe({
                    moviesResponseState.postValue(ViewState.Success)
                }, { error -> handleFailure(error) })
                .addToComposite(compositeDisposable)
        }
    }

    private fun hasNextPage() = page <= totalPages || totalPages == -1
}