package com.arctouch.codechallenge.base.common.network

import android.content.Context
import com.arctouch.codechallenge.base.extensions.networkInfo
import com.arctouch.codechallenge.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which handles device network connection.
 */
@OpenForTesting
@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected ?: false
}