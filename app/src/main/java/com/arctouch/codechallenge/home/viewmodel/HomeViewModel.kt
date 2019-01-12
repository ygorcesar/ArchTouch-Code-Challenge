package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.extensions.addToComposite
import com.arctouch.codechallenge.base.extensions.observeOnComputation
import com.arctouch.codechallenge.base.presentation.BaseViewModel
import com.arctouch.codechallenge.home.interactor.HomeInteractor
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor
) : BaseViewModel() {

    val moviesResponseState = MutableLiveData<ViewState>()
    private var page: Long = 1L

    fun getUpcomingMovies() {
        Timber.i("INICIOU A CHAMADAAAAA -------------->")
        compositeDisposable.clear()

        val calls = Completable.concat(
            listOf(
                homeInteractor.getGenres(),
                homeInteractor.upcomingMovies(page)
                    .doOnSuccess { movies ->
                        if (page <= movies.totalPages) page.inc()
                        moviesResponseState.postValue(ViewState.Complete(movies))
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