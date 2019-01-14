package com.arctouch.codechallenge.base.data

import com.arctouch.codechallenge.base.common.exception.EssentialParamMissingException
import io.reactivex.functions.Function

/**
 * Base mapper to all Mappers extend
 *
 * @param Raw The result from server
 * @param Model The object to create from Raw
 */
abstract class BaseMapper<Raw, Model> : Function<Raw, Model> {

    private var missingParams = StringBuilder("[")

    abstract val trackException: (Throwable) -> Unit

    fun addMissingParam(param: String) {
        missingParams.append("$param,")
    }

    private fun getMissingParams() = missingParams.toString()

    private fun closeMissingParams() = "${getMissingParams()}]"

    protected fun isMissingParams() = closeMissingParams() != "[]"

    protected fun resetMissingParams() {
        missingParams = StringBuilder("[")
    }

    fun throwException(raw: Any) {
        val essentialParamException =
            EssentialParamMissingException(getMissingParams(), rawObject = raw)
        trackException(essentialParamException)
        resetMissingParams()
        throw essentialParamException
    }

    @Throws(EssentialParamMissingException::class)
    override fun apply(raw: Raw): Model {
        assertEssentialParams(raw)
        if (isMissingParams()) throwException(raw as Any)
        return copyValues(raw)
    }

    /**
     * Check if the required parameters were return from server
     *
     * @param raw The result from server
     * @throws EssentialParamMissingException When a required parameter is missing
     */
    abstract fun assertEssentialParams(raw: Raw)

    /**
     * Create a [Model] using the values in [Raw]
     *
     * @param raw The result from server
     * @return A model with the raw's values
     */
    abstract fun copyValues(raw: Raw): Model
}