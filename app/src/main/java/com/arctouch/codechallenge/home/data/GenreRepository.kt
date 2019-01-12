package com.arctouch.codechallenge.home.data

import com.arctouch.codechallenge.base.common.exception.NeedFetchData
import com.arctouch.codechallenge.base.common.network.NetworkHandler
import com.arctouch.codechallenge.base.data.BaseRemoteRepository
import com.arctouch.codechallenge.home.model.Genre
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.manager.session.SessionManager
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val remote: GenreRepository.Remote,
    private val local: GenreRepository.Local
) {

    interface Contract {

        fun getGenres(): Single<List<Genre>>

    }

    interface LocalContract : Contract {
        fun saveGenres(genres: List<Genre>)
    }

    fun getGenres(): Completable = local.getGenres().onErrorResumeNext(
        remote.getGenres().doOnSuccess { genres ->
            local.saveGenres(genres)
        }
    ).ignoreElement()

    class Local
    @Inject constructor(
        private val sessionManager: SessionManager
    ) : GenreRepository.LocalContract {
        override fun getGenres(): Single<List<Genre>> {
            return Single.create<List<Genre>> { emitter ->
                val genres = sessionManager.genres
                when {
                    genres.isNotEmpty() -> emitter.onSuccess(genres)
                    else -> emitter.onError(NeedFetchData)
                }
            }
        }

        override fun saveGenres(genres: List<Genre>) {
            sessionManager.genres = genres
        }
    }

    class Remote
    @Inject constructor(
        private val genresMapper: GenresMapper,
        private val genreService: GenreService,
        private val queryParams: QueryParams,
        networkHandler: NetworkHandler
    ) : BaseRemoteRepository(networkHandler), Contract {

        override fun getGenres(): Single<List<Genre>> = request(genresMapper) {
            genreService.genres(queryParams.queryMap)
        }
    }
}