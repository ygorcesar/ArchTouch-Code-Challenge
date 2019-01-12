package com.arctouch.codechallenge.base.data

sealed class ViewState {
    object Loading : ViewState()
    object Success : ViewState()
    data class Complete<T>(val response: T) : ViewState()
}

inline fun <reified T> ViewState.Complete<*>?.getList(): List<T> {
    (this?.response as List<*>?)?.filterIsInstance(T::class.java)?.let {
        return it
    }
    return emptyList()
}

inline fun <reified T> ViewState.Complete<*>?.getPaginationItems(): List<T> {
    return (this?.response as BasePagination<*>?)?.getItems() ?: emptyList()
}