package com.arctouch.codechallenge.movie

import com.arctouch.codechallenge.base.BaseTests
import com.arctouch.codechallenge.movie.data.MoviesRepository
import com.arctouch.codechallenge.movie.domain.MovieDetailsBusiness
import com.arctouch.codechallenge.movie.domain.MovieDetailsInteractor
import com.arctouch.codechallenge.utils.TestFakeData.movieDetails
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever

class MovieDetailsInteractorTests : BaseTests() {

    @Mock
    lateinit var repository: MoviesRepository

    private lateinit var interactor: MovieDetailsInteractor

    @Before
    fun setUp() {
        interactor = MovieDetailsInteractor(repository)
    }

    @Test
    fun `Should fetch movie details with success`() {
        whenever(repository.movieDetails(1L))
            .thenReturn(Single.just(movieDetails))

        val testObserver = interactor.movieDetails(1L).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

    @Test
    fun `Should throw invalid movie on fetch movie details with invalid id value`() {

        val testObserver = interactor.movieDetails(-1L).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(MovieDetailsBusiness.MovieIdInvalid)

        val testObserver2 = interactor.movieDetails(null).test()

        testObserver2.awaitTerminalEvent()

        testObserver2.assertNotComplete()
        testObserver2.assertError(MovieDetailsBusiness.MovieIdInvalid)
    }

}