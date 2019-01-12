package com.arctouch.codechallenge.manager.data

import com.arctouch.codechallenge.application.di.modules.NetworkModule
import javax.inject.Inject
import javax.inject.Named

class QueryParams @Inject constructor(
    @Named(NetworkModule.API_KEY_NAMED) private val apiKey: String
) {

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_REGION = "region"
        private const val QUERY_PARAM_SEARCH = "query"
    }

    val queryMap by lazy { mutableMapOf<String, String>().apply { initialize(this) } }

    private var query: String? = null
        set(value) {
            field = value
            if (value == null) queryMap.remove(QUERY_PARAM_SEARCH)
            else queryMap[QUERY_PARAM_SEARCH] = value
        }

    private var page: Long = 1L
        set(value) {
            field = value
            queryMap[QUERY_PARAM_PAGE] = value.toString()
        }

    private var totalPages: Int = -1

    private var language: String = NetworkModule.DEFAULT_LANGUAGE
        set(value) {
            field = value
            queryMap[QUERY_PARAM_LANGUAGE] = value
        }

    private var region: String? = null
        set(value) {
            field = value
            if (value == null) queryMap.remove(QUERY_PARAM_REGION)
            else queryMap[QUERY_PARAM_REGION] = value
        }

    fun setQueryFilter(query: String?) {
        this.query = query
    }

    fun setQueryParams(
        page: Long = 1L,
        search: String? = null,
        language: String = NetworkModule.DEFAULT_LANGUAGE,
        region: String? = null
    ) {
        this.page = page
        this.query = search
        this.language = language
        this.region = region
    }

    fun hasNextPage(): Boolean {
        page = page
        return page <= totalPages || totalPages == -1
    }

    fun incrementPage(pagesAvailable: Int) {
        this.totalPages = pagesAvailable
        if (page <= pagesAvailable) page = page.inc()
    }

    fun clear() {
        setQueryParams()
        totalPages = -1
        queryMap.clear()
        initialize(queryMap)
    }

    private fun initialize(map: MutableMap<String, String>) {
        map[QUERY_PARAM_API_KEY] = apiKey
        map[QUERY_PARAM_LANGUAGE] = language
    }

}