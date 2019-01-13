package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.extensions.addToComposite
import com.arctouch.codechallenge.base.extensions.observeOnComputation
import com.arctouch.codechallenge.base.presentation.BaseViewModel
import com.arctouch.codechallenge.home.interactor.HomeInteractor
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.movie.model.Movie
import io.reactivex.Completable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor,
    private val queryParams: QueryParams
) : BaseViewModel() {

    val moviesResponseState = MutableLiveData<ViewState>()
    private val movies = mutableListOf<Movie>()

    fun getUpcomingMovies(nextPage: Boolean = false, clearItemsBeforeRequest: Boolean = false) {
        if (clearItemsBeforeRequest) clearItems()

        if (movies.isNotEmpty() && !nextPage) {
            moviesResponseState.postValue(ViewState.Complete(movies))
        } else if (queryParams.hasNextPage()) {
            compositeDisposable.clear()

            val calls = Completable.concat(
                listOf(
                    homeInteractor.getGenres(),
                    homeInteractor.upcomingMovies(queryParams)
                        .doOnSuccess { movies -> onResultSuccess(movies) }.ignoreElement()
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

    fun searchMovies(query: String?, nextPage: Boolean = false) {
        if (!nextPage) clearItems()

        queryParams.setQueryFilter(query)
        if (queryParams.hasNextPage()) {
            compositeDisposable.clear()
            homeInteractor.searchMovies(queryParams)
                .observeOnComputation()
                .doOnSubscribe { moviesResponseState.postValue(ViewState.Loading) }
                .subscribe({ movies -> onResultSuccess(movies) }, { error -> handleFailure(error) })
                .addToComposite(compositeDisposable)
        }
    }

    private fun onResultSuccess(movies: BasePagination<Movie>) {
        queryParams.incrementPage(movies.totalPages)
        this.movies.addAll(movies.results)
        moviesResponseState.postValue(ViewState.Complete(movies.results))
    }

    private fun clearItems() {
        movies.clear()
        queryParams.clear()
    }
}