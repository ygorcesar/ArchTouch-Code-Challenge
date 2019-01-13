package com.arctouch.codechallenge.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(AndroidJUnit4::class)
abstract class BaseEspressoTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    val app: MockArcTouchApplication
        get() = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as MockArcTouchApplication
}