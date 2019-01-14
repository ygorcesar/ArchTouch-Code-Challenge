package com.arctouch.codechallenge.base.data

data class BasePagination<T>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val totalResults: Int
)

inline fun <reified T> BasePagination<*>.getItems(): List<T> = this.results.filterIsInstance<T>()