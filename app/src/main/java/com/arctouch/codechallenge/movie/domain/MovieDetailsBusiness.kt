package com.arctouch.codechallenge.movie.domain

import com.arctouch.codechallenge.base.common.exception.BusinessException

sealed class MovieDetailsBusiness {

    object MovieIdInvalid : BusinessException()
}