package com.arctouch.codechallenge.utils

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.di.base
import com.arctouch.codechallenge.home.model.Genre
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.movie.model.Movie
import com.arctouch.codechallenge.movie.model.MovieDetails
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import org.mockito.Mockito

fun mockHomeViewModel(): HomeViewModel {
    val moviesResponseState = MutableLiveData<ViewState>()
    val appException = MutableLiveData<AppException>()

    val homeViewModel = Mockito.mock(HomeViewModel::class.java)
    val viewModelFactory = base().viewModelFactory

    Mockito.`when`(viewModelFactory.create(HomeViewModel::class.java))
        .thenReturn(homeViewModel)

    Mockito.`when`(homeViewModel.moviesResponseState).thenReturn(moviesResponseState)
    Mockito.`when`(homeViewModel.appException).thenReturn(appException)

    return homeViewModel
}

val genre: Genre = Genre(1, "Ação")

val movie: Movie = Movie(
    1,
    "Batman",
    "O cavaleiro das trevas ressurge =)",
    listOf(genre),
    "poster_path",
    "backdrop_path",
    "06/12/2012"
)

val movies: List<Movie> = listOf(
    movie,
    movie,
    movie,
    movie,
    movie,
    movie,
    movie,
    movie,
    movie
)


fun mockMovieDetailsViewModel(): MovieDetailsViewModel {
    val movieDetailsResponseState = MutableLiveData<ViewState>()
    val appException = MutableLiveData<AppException>()

    val movieDetailsViewModel = Mockito.mock(MovieDetailsViewModel::class.java)
    val viewModelFactory = base().viewModelFactory

    Mockito.`when`(viewModelFactory.create(MovieDetailsViewModel::class.java))
        .thenReturn(movieDetailsViewModel)

    Mockito.`when`(movieDetailsViewModel.movieDetailsResponseState)
        .thenReturn(movieDetailsResponseState)

    Mockito.`when`(movieDetailsViewModel.appException).thenReturn(appException)

    return movieDetailsViewModel
}

val movieDetails: MovieDetails = MovieDetails(
    1,
    "Batman",
    "O cavaleiro das trevas ressurge =)",
    listOf(genre),
    "poster_path",
    "backdrop_path",
    "06/12/2012",
    1,
    32,
    8.5
)