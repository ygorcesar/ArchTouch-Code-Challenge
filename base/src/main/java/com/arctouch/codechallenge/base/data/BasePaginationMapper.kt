package com.arctouch.codechallenge.base.data

abstract class BasePaginationMapper<Raw, Model> :
    BaseMapper<BasePaginationRaw<Raw>, BasePagination<Model>>() {

    override fun assertEssentialParams(raw: BasePaginationRaw<Raw>) {

        if (raw.page == null) addMissingParam("paginationPage")

        if (raw.totalPages == null) addMissingParam("paginationTotalPages")

        if (raw.totalResults == null) addMissingParam("paginationTotalResults")


        if (raw.results == null) addMissingParam("paginationResults")

        if (isMissingParams()) throwException(raw)

        raw.results?.forEach { rawItem -> assertPaginationEssentialParams(rawItem) }

        if (isMissingParams()) throwException(raw as Any)
    }

    override fun copyValues(raw: BasePaginationRaw<Raw>): BasePagination<Model> {
        return BasePagination(
            page = raw.page!!,
            totalPages = raw.totalPages!!,
            totalResults = raw.totalResults!!,
            results = raw.results?.map { rawItem -> copyPaginationRawToModel(rawItem) } ?: listOf()
        )
    }


    abstract fun assertPaginationEssentialParams(pageRawItem: Raw)

    abstract fun copyPaginationRawToModel(rawItem: Raw): Model

}