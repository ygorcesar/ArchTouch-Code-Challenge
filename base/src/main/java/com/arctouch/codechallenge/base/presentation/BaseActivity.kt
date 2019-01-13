package com.arctouch.codechallenge.base.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.base.R
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.common.exception.HttpError
import com.arctouch.codechallenge.base.common.exception.NetworkError
import com.arctouch.codechallenge.base.common.exception.UnknownException
import com.arctouch.codechallenge.base.di.base
import com.arctouch.codechallenge.base.presentation.views.EmptyView
import com.arctouch.codechallenge.base.presentation.views.ErrorView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutResId: Int

    val compositeDisposable by lazy { CompositeDisposable() }

    open val baseErrorView: ErrorView? = null
    open val baseEmptyView: EmptyView? = null

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

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) compositeDisposable.clear()
        super.onDestroy()
    }

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
                baseErrorView?.showError()
            }
        }
        loading(false)
    }

    /**
     * Function called when handled a Http generic exception
     */
    open fun onHttpError() {
        baseErrorView?.showError()
    }

    /**
     * Function called when there is no internet connection
     */
    open fun onNetworkWithoutConnection() {
        baseErrorView?.showConnectionError()
    }

    open fun onUnknownError() {
        baseErrorView?.showError()
    }

    open fun loading(isLoading: Boolean) {
        if (isLoading) hideEmptyView()
    }

    open fun toggleEmptyView(items: List<Any>) {
        if (items.isEmpty()) showEmptyView() else hideEmptyView()
    }

    open fun showEmptyView(
        title: Int = R.string.action_error_ops,
        subTitle: Int = R.string.empty_view_no_information_found
    ) {
        loading(false)
        baseEmptyView?.show(title, subTitle)
    }

    open fun hideEmptyView() {
        baseEmptyView?.hide()
    }
}