package com.arctouch.codechallenge.movie

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseEspressoTest
import com.arctouch.codechallenge.base.common.exception.HttpError
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.movie.presentation.MovieDetailsActivity
import com.arctouch.codechallenge.movie.presentation.MovieDetailsActivityArgs
import com.arctouch.codechallenge.movie.viewmodel.MovieDetailsViewModel
import com.arctouch.codechallenge.utils.mockHomeViewModel
import com.arctouch.codechallenge.utils.mockMovieDetailsViewModel
import com.arctouch.codechallenge.utils.movie
import com.arctouch.codechallenge.utils.movieDetails
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class MovieDetailsActivityTest : BaseEspressoTest() {

    @Rule
    @JvmField
    val activityRule = IntentsTestRule(MovieDetailsActivity::class.java, false, false)

    private val viewModel: MovieDetailsViewModel = mockMovieDetailsViewModel()

    /**
     * Handle mocks of HomeActivity
     * */
    private val homeViewModel: HomeViewModel = mockHomeViewModel()

    @Before
    fun setup() {
        val intent = MovieDetailsActivityArgs(movie.id).intent(app)

        activityRule.launchActivity(intent)
    }

    @Test
    fun whenIsFetchingData_shouldShowLoadingView() {
        activityRule.runOnUiThread {
            viewModel.movieDetailsResponseState.value = ViewState.Loading
        }

        assertDisplayed(R.id.movieDetailsProgress)
        assertNotDisplayed(R.id.errorView)
    }

    @Test
    fun whenErrorOnFetchData_shouldShowErrorView() {
        activityRule.runOnUiThread {
            viewModel.appException.value = HttpError
        }

        assertDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.movieDetailsProgress)
    }

    @Test
    fun whenFetchedData_shouldShowMovieInformation() {
        activityRule.runOnUiThread {
            viewModel.movieDetailsResponseState.value = ViewState.Complete(movieDetails)
        }

        assertDisplayed(R.id.movieDetailsTitle)
        assertDisplayed(R.id.movieDetailsOverview)
        assertDisplayed(R.id.movieDetailsImage)
        assertDisplayed(R.id.movieDetailsBackdropImage)
        assertDisplayed(R.id.movieDetailsRatingIcon)
        assertDisplayed(R.id.movieDetailsRating)
        assertDisplayed(R.id.movieDetailsDurationIcon)
        assertDisplayed(R.id.movieDetailsDuration)
        assertDisplayed(R.id.movieDetailsGenres)
        assertDisplayed(R.id.movieDetailsBackButton)

        assertNotDisplayed(R.id.movieDetailsProgress)
        assertNotDisplayed(R.id.errorView)

        assertDisplayed(R.string.movie_details_overview_label)
        assertDisplayed(R.string.movie_details_genres_label)

        val movieDuration = activityRule.activity.getMovieDuration(movieDetails)

        assertDisplayed(movieDetails.title)
        assertDisplayed(movieDetails.overview)
        assertDisplayed(movieDetails.rating.toString())
        assertDisplayed(movieDuration)

        assertHasDrawable(R.id.movieDetailsBackButton, R.drawable.ic_arrow_back)
        assertHasDrawable(R.id.movieDetailsRatingIcon, R.drawable.ic_stars)
        assertHasDrawable(R.id.movieDetailsDurationIcon, R.drawable.ic_time)
    }

    @Test
    fun whenClickBackButton_shouldBackToHome() {
        clickOn(R.id.movieDetailsBackButton)

        assertTrue(activityRule.activity.isFinishing)
    }
}