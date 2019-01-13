package com.arctouch.codechallenge.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.manager.NavigationManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class MockModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.Factory =
        Mockito.mock(ViewModelProvider.Factory::class.java)

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager = Mockito.mock(NavigationManager::class.java)

}