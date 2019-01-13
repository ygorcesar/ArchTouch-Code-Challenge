package com.arctouch.codechallenge.movie

import com.arctouch.codechallenge.base.BaseViewModelTests
import com.arctouch.codechallenge.base.common.exception.NetworkError
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.movie.domain.MovieDetailsInteractor
import com.arctouch.codechallenge.movie.model.MovieDetails
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import com.arctouch.codechallenge.utils.TestFakeData.movieDetails
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever

class MovieDetailsViewModelTests : BaseViewModelTests() {

    @Mock
    lateinit var interactor: MovieDetailsInteractor

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(interactor)
    }

    @Test
    fun `Should fetch movie details with success`() {
        val publish = PublishSubject.create<MovieDetails>()
        val single = Single.just(movieDetails)
            .delaySubscription(publish)

        whenever(interactor.movieDetails(1L))
            .thenReturn(single)

        viewModel.getMovieDetails(1L)

        assertThat(viewModel.movieDetailsResponseState.value).isExactlyInstanceOf(ViewState.Loading.javaClass)

        publish.onComplete()

        assertThat(viewModel.movieDetailsResponseState.value).isInstanceOf(ViewState.Complete::class.java)
    }

    @Test
    fun `Should throw error on fetch movie details`() {
        val publish = PublishSubject.create<MovieDetails>()
        val single = Single.just(movieDetails)
            .delaySubscription(publish)

        whenever(interactor.movieDetails(1L))
            .thenReturn(single)

        viewModel.getMovieDetails(1L)

        assertThat(viewModel.movieDetailsResponseState.value).isExactlyInstanceOf(ViewState.Loading.javaClass)

        publish.onError(NetworkError)

        assertThat(viewModel.appException.value).isEqualToComparingFieldByField(NetworkError.javaClass)
    }

}