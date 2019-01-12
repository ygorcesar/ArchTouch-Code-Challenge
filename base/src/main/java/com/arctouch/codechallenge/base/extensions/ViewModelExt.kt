package com.arctouch.codechallenge.base.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.arctouch.codechallenge.base.common.exception.AppException

inline fun <reified VM : ViewModel> AppCompatActivity.provideViewModel() = lazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.provideViewModel() = lazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

/**
 * Extension function to get a [ViewModel] from [ViewModelProviders]
 * using this factory and a body to execute actions with this ViewModel
 *
 * @param factory The factory used to create the ViewModel
 * @param body The actions to execute with this ViewModel
 * @return The instance created before or a new instance
 */
inline fun <reified T : ViewModel> Fragment.provideViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.provideViewModelWithActivity(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(activity!!, factory)[T::class.java]
    vm.body()
    return vm
}


/**
 * Extension function to get a [ViewModel] from [ViewModelProviders]
 * using this factory and a body to execute actions with this ViewModel
 *
 * @param factory The factory used to create the ViewModel
 * @param body The actions to execute with this ViewModel
 * @return The instance created before or a new instance
 */
inline fun <reified T : ViewModel> FragmentActivity.provideViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

/**
 * Extension function to attach a behavior to any [LiveData]
 *
 * @param liveData The current [LiveData]
 * @param body The action to execute
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

/**
 * Extension function to attach a behavior to fullbankException [LiveData]
 *
 * @param liveData The current [LiveData]
 * @param body The action to execute using the [AppException]
 */
fun <L : LiveData<com.arctouch.codechallenge.base.common.exception.AppException>> LifecycleOwner.failure(
    liveData: L,
    body: (com.arctouch.codechallenge.base.common.exception.AppException?) -> Unit
) =
    liveData.observe(this, Observer(body))