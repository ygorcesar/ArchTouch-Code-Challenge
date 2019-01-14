package com.arctouch.codechallenge.application.di.modules

import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.base.di.ViewModelKey
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun provideMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel

}