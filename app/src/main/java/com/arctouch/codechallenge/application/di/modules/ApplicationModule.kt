package com.arctouch.codechallenge.application.di.modules

import android.app.Application
import android.content.Context
import com.arctouch.codechallenge.application.di.scopes.ApplicationScope
import com.arctouch.codechallenge.movie.data.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun provideMoviesRepository(dataSource: MoviesRepository.Remote): MoviesRepository = dataSource

}