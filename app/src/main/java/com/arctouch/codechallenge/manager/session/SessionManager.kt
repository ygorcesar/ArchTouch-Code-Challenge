package com.arctouch.codechallenge.manager.session

import com.arctouch.codechallenge.home.model.Genre
import com.orhanobut.hawk.Hawk
import javax.inject.Inject

class SessionManager @Inject constructor() : SessionContract {

    companion object {
        private const val PREF_KEY_GENRES = "genres"
    }

    override var genres: List<Genre>
        get() = Hawk.get(PREF_KEY_GENRES) ?: listOf()
        set(genres) {
            Hawk.put(PREF_KEY_GENRES, genres)
        }

}