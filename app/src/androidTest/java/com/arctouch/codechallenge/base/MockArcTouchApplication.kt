package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.application.ArcTouchApplication
import com.arctouch.codechallenge.application.di.ApplicationComponent

class MockArcTouchApplication : ArcTouchApplication() {

    override fun createApplicationComponent(): ApplicationComponent {
        return DaggerMockApplicationComponent
            .builder()
            .mockModule(MockModule(this@MockArcTouchApplication))
            .build()
    }
}