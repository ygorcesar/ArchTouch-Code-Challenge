package com.arctouch.codechallenge.manager

import timber.log.Timber

object LoggerManager {

    /**
     * Track exception to Crashlytics
     * */
    fun trackException(exception: Throwable, msg: String? = null) {
        Timber.e(exception)
        Timber.e(msg)
    }

}