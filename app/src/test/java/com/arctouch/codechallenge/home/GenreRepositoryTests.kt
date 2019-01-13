package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.base.BaseTests
import com.arctouch.codechallenge.base.common.exception.NeedFetchData
import com.arctouch.codechallenge.base.common.exception.NetworkError
import com.arctouch.codechallenge.home.data.GenreRepository
import com.arctouch.codechallenge.home.data.GenreService
import com.arctouch.codechallenge.home.data.GenresMapper
import com.arctouch.codechallenge.manager.data.QueryParams
import com.arctouch.codechallenge.manager.session.SessionManager
import com.arctouch.codechallenge.utils.TestFakeData.genres
import com.arctouch.codechallenge.utils.TestFakeData.genresRawResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever

class GenreRepositoryTests : BaseTests() {

    @Mock
    lateinit var sessionManager: SessionManager

    @Mock
    lateinit var service: GenreService

    @Mock
    lateinit var queryParams: QueryParams

    private val mapper = GenresMapper()

    private lateinit var repositoryLocal: GenreRepository.Local
    private lateinit var repositoryRemote: GenreRepository.Remote

    @Before
    fun setup() {
        repositoryLocal = GenreRepository.Local(sessionManager)
        repositoryRemote = GenreRepository.Remote(mapper, service, queryParams, networkHandler)
    }

    @Test
    fun `Should fetch data on local repository`() {
        whenever(sessionManager.genres).thenReturn(genres)

        val testObserver = repositoryLocal.getGenres().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(genres)
    }

    @Test
    fun `Should cause error need fetch data on empty local repository`() {
        whenever(sessionManager.genres).thenReturn(listOf())

        val testObserver = repositoryLocal.getGenres().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNotComplete()
        testObserver.assertFailure(NeedFetchData::class.java)
        testObserver.assertError(NeedFetchData)
    }

    @Test
    fun `Should fetch data on remote repository`() {
        whenever(networkHandler.isConnected)
            .thenReturn(true)

        whenever(service.genres(queryParams.queryMap))
            .thenReturn(Single.just(genresRawResponse))

        val testObserver = repositoryRemote.getGenres().test()

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

    @Test
    fun `Should throw NetworkError on fetch genres from remote repository`() {
        whenever(networkHandler.isConnected)
            .thenReturn(false)

        val testObserver = repositoryRemote.getGenres().test()

        testObserver.assertNotComplete()
        testObserver.assertError(NetworkError)
    }
}