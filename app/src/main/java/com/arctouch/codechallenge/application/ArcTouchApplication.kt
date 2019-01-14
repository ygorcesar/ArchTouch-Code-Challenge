package com.arctouch.codechallenge.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.application.di.ApplicationComponent
import com.arctouch.codechallenge.application.di.DaggerApplicationComponent
import com.arctouch.codechallenge.application.di.modules.ApplicationModule
import com.arctouch.codechallenge.base.di.BaseComponent
import com.orhanobut.hawk.Hawk
import timber.log.Timber

open class ArcTouchApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setupTimber()
        setupHawk()

        val appComponent = createApplicationComponent()
        ApplicationComponent.INSTANCE = appComponent
        BaseComponent.INSTANCE = appComponent
    }

    open fun createApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this@ArcTouchApplication))
            .build()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHawk() {
        Hawk.init(this)
            .setLogInterceptor { message -> Timber.i("HAWK: $message") }
            .build()
    }

}