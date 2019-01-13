package com.arctouch.codechallenge.home

import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseEspressoTest
import com.arctouch.codechallenge.base.common.exception.HttpError
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.home.presentation.HomeActivity
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.movie.model.Movie
import com.arctouch.codechallenge.movie.presentation.MovieDetailsActivity
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import com.arctouch.codechallenge.utils.*
import com.schibsted.spain.barista.assertion.BaristaAssertions.assertThatBackButtonClosesTheApp
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when` as whenever

@LargeTest
class HomeActivityTest : BaseEspressoTest() {

    @Rule
    @JvmField
    val activityRule = IntentsTestRule(HomeActivity::class.java, false, false)

    private val viewModel: HomeViewModel = mockHomeViewModel()

    /**
     * Handle mocks when go to MovieDetailsActivity
     * */
    private val movieDetailsViewState: MovieDetailsViewModel = mockMovieDetailsViewModel()

    @Before
    fun setup() {
        activityRule.launchActivity(null)
    }

    @Test
    fun whenActivityLaunchedShowInitialState() {
        assertDisplayed(R.id.homeToolbar)
        assertDisplayed(R.id.recyclerView)
        assertNotDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.emptyView)
    }

    @Test
    fun whenIsFetchingData_shouldShowLoadingView() {
        activityRule.runOnUiThread {
            viewModel.moviesResponseState.value = ViewState.Loading
        }

        assertDisplayed(R.id.skeletonView)
        assertNotDisplayed(R.id.emptyView)
        assertNotDisplayed(R.id.errorView)
    }

    @Test
    fun whenErrorOnFetchData_shouldShowErrorView() {
        activityRule.runOnUiThread { viewModel.appException.value = HttpError }

        assertDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.emptyView)
        assertNotDisplayed(R.id.skeletonView)
    }

    @Test
    fun whenFetchEmptyData_shouldShowEmptyView() {
        activityRule.runOnUiThread {
            viewModel.moviesResponseState.value = ViewState.Complete(listOf<Movie>())
        }

        assertDisplayed(R.id.emptyView)
        assertNotDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.skeletonView)
    }

    @Test
    fun whenFetchedData_shouldShowRecyclerViewItems() {
        activityRule.runOnUiThread {
            viewModel.moviesResponseState.value = ViewState.Complete(movies)
        }

        assertCustomRecyclerViewItemCount(R.id.recyclerView, movies.size)
    }

    @Test
    fun whenClickMovieItem_shouldGoToMovieDetails() {
        activityRule.runOnUiThread {
            viewModel.moviesResponseState.value = ViewState.Complete(movies)
        }

        clickRecyclerViewItem(R.id.recyclerView, 1)

        intended(hasComponent(MovieDetailsActivity::class.java.name))
    }

    @Test
    fun whenBackButtonAction_shouldClosesTheApp() {
        assertThatBackButtonClosesTheApp()
    }

}