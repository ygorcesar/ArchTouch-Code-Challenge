package com.arctouch.codechallenge.home.presentation

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.common.exception.AppException
import com.arctouch.codechallenge.base.data.ViewState
import com.arctouch.codechallenge.base.data.getList
import com.arctouch.codechallenge.base.extensions.addToComposite
import com.arctouch.codechallenge.base.extensions.isVisible
import com.arctouch.codechallenge.base.extensions.observe
import com.arctouch.codechallenge.base.extensions.provideViewModel
import com.arctouch.codechallenge.base.presentation.BaseActivity
import com.arctouch.codechallenge.home.model.Movie
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_recycler_view.*
import kotlinx.android.synthetic.main.home_activity.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    override val layoutResId = R.layout.home_activity
    private lateinit var viewModel: HomeViewModel
    private val homeAdapter by lazy { HomeAdapter() }
    private var savedRecyclerLayoutState: Parcelable? = null
    val searchTextObservable = PublishSubject.create<String>()
    private val query = MutableLiveData<String>()

    override fun onInit() {
        viewModel = provideViewModel(viewModelFactory) {
            observe(moviesResponseState, ::onMoviesResponse)
            observe(appException, ::onResponseError)
            observe(query, ::onQueryChanged)
            getUpcomingMovies()
        }
        setSupportActionBar(homeToolbar)
        observeSearchQuery()
    }

    override fun onResume() {
        super.onResume()
        savedRecyclerLayoutState?.let {
            customRecyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }

    private fun onMoviesResponse(viewState: ViewState?) {
        when (viewState) {
            ViewState.Loading -> loading(true)
            is ViewState.Complete<*> -> {
                showResults(viewState.getList())
            }
            else -> loading(false)
        }
    }

    private fun onResponseError(appException: AppException?) {
        checkResponseException(appException) {}
    }

    private fun showResults(movies: List<Movie>) {
        loading(false)
        recyclerView?.apply {
            if (adapter == null) {
                setLinearLayout()
                setAdapter(homeAdapter)
                addOnScrollListener { requestNewPages() }
            }
        }
        homeAdapter.addItems(movies)
    }

    private fun requestNewPages() {
        val querySearch = query.value ?: ""
        when {
            querySearch.length >= 3 -> viewModel.searchMovies(querySearch, nextPage = true)
            else -> viewModel.getUpcomingMovies(nextPage = true)
        }
    }

    private fun onQueryChanged(query: String?) {
        when {
            query?.length ?: 0 >= 3 -> {
                homeAdapter.clearItems()
                viewModel.searchMovies(query)
            }
            else -> {
                homeAdapter.clearItems()
                viewModel.getUpcomingMovies(clearItemsBeforeRequest = true)
            }
        }
    }

    private fun observeSearchQuery() {
        searchTextObservable.debounce(1, TimeUnit.SECONDS)
            .subscribe({ query.postValue(it) }, { Timber.e(it) })
            .addToComposite(compositeDisposable)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        menu?.findItem(R.id.action_search)?.let { searchItem ->
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = getString(R.string.home_hint_search_movies)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val query = newText ?: ""
                    when {
                        query.length >= 3 -> searchTextObservable.onNext(query)
                        query.isBlank() -> searchTextObservable.onNext("")
                    }
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun loading(isLoading: Boolean) {
        when {
            homeAdapter.getItems().isEmpty() -> progressBar.isVisible = isLoading
            else -> recyclerView?.loading = isLoading
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(
            KEY_RECYCLER_VIEW_STATE,
            customRecyclerView.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedRecyclerLayoutState = savedInstanceState?.getParcelable(KEY_RECYCLER_VIEW_STATE)
    }

    companion object {
        private const val KEY_RECYCLER_VIEW_STATE = "recycler_view_state"
    }
}
