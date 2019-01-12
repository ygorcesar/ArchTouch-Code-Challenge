package com.arctouch.codechallenge.application.di

import android.content.Context
import com.arctouch.codechallenge.application.di.modules.ApplicationModule
import com.arctouch.codechallenge.application.di.modules.NetworkModule
import com.arctouch.codechallenge.application.di.scopes.ApplicationScope
import com.arctouch.codechallenge.base.di.BaseComponent
import com.arctouch.codechallenge.base.di.BaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * Function to get the current [ApplicationComponent] instance
 */
fun app() = ApplicationComponent.INSTANCE

@ApplicationScope
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        BaseModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent : BaseComponent {

    val context: Context

    companion object {
        lateinit var INSTANCE: ApplicationComponent
    }
}
