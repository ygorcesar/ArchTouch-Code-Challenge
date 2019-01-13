package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.base.BaseViewModelTests
import com.arctouch.codechallenge.base.common.exception.NetworkError
import com.arctouch.codechallenge.base.data.BasePagination
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.home.interactor.HomeInteractor
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.movie.model.Movie
import com.arctouch.codechallenge.utils.TestFakeData.paginationMovies
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever

class HomeViewModelTests : BaseViewModelTests() {

    @Mock
    lateinit var interactor: HomeInteractor

    private val queryParams = QueryParams("")

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(interactor, queryParams)
    }

    @Test
    fun `Should fetch movies with success`() {
        val publish = PublishSubject.create<BasePagination<Movie>>()
        val single = Single.just(paginationMovies)
            .delaySubscription(publish)

        whenever(interactor.upcomingMovies(queryParams))
            .thenReturn(single)

        viewModel.getUpcomingMovies()

        assertThat(viewModel.moviesResponseState.value).isExactlyInstanceOf(ViewState.Loading.javaClass)

        publish.onComplete()

        assertThat(viewModel.moviesResponseState.value).isEqualToComparingFieldByField(ViewState.Complete::class.java)
    }

    @Test
    fun `Should throw error on fetch movies`() {
        val publish = PublishSubject.create<BasePagination<Movie>>()
        val single = Single.just(paginationMovies)
            .delaySubscription(publish)

        whenever(interactor.upcomingMovies(queryParams))
            .thenReturn(single)

        viewModel.getUpcomingMovies()

        assertThat(viewModel.moviesResponseState.value).isExactlyInstanceOf(ViewState.Loading.javaClass)

        publish.onError(NetworkError)

        assertThat(viewModel.appException.value).isEqualToComparingFieldByField(NetworkError.javaClass)
    }

}