package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.application.di.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockModule::class])
interface MockApplicationComponent : ApplicationComponent