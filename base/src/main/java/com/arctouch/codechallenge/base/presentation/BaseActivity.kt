package com.arctouch.codechallenge.base.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.common.exception.HttpError
import com.arctouch.codechallenge.base.common.exception.NetworkError
import com.arctouch.codechallenge.base.common.exception.UnknownException
import com.arctouch.codechallenge.base.di.base
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutResId: Int

    val viewModelFactory: ViewModelProvider.Factory by lazy {
        base().viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        onInit()
    }

    /**
     * Function called onCreate Activity after setContentView
     * */
    abstract fun onInit()

    /**
     * Check commons exception between application
     *
     * @param appException The exception returned from backend
     * @param body The function to check specific business exceptions
     */
    fun checkResponseException(appException: AppException?, body: (AppException?) -> Unit) {
        Timber.e(appException)
        when (appException) {
            NetworkError -> {
                onNetworkWithoutConnection()
            }
            HttpError -> {
                onHttpError()
            }
            UnknownException -> {
                onUnknownError()
            }
            else -> {
                body(appException)
            }
        }
        loading(false)
    }

    /**
     * Function called when handled a Http generic exception
     */
    open fun onHttpError() {}

    /**
     * Function called when there is no internet connection
     */
    open fun onNetworkWithoutConnection() {

    }

    open fun onUnknownError() {

    }

    open fun loading(isLoading: Boolean) {

    }
}