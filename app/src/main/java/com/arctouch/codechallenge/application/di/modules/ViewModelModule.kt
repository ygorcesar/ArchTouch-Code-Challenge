package com.arctouch.codechallenge.application.di.modules

import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.base.di.ViewModelKey
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}