package com.arctouch.codechallenge.base.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Extension property to get a [NetworkInfo]
 *
 * @return The active network info in the Android Framework
 */
val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo