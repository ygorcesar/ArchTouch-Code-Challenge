package com.arctouch.codechallenge.base.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.testing.OpenForTesting
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

@OpenForTesting
abstract class BaseViewModel : ViewModel() {

    val appException = MutableLiveData<AppException>()

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun handleFailure(appException: Throwable) {
        if (appException is com.arctouch.codechallenge.base.common.exception.AppException) {
            this.appException.postValue(appException)
        } else {
            this.appException.postValue(com.arctouch.codechallenge.base.common.exception.UnknownException)
        }
        Timber.e(appException)
    }
}