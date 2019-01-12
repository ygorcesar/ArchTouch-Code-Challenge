package com.arctouch.codechallenge.base.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair

interface ActivityArgs {
    /**
     * Create a intent to launch
     *
     * @param context The current context
     * @return returns an intent that can be used to launch this activity.
     */
    fun intent(context: Context): Intent

    /**
     * Launches the activity given your activity.
     *
     * The default implementation uses the intent generated from [intent]
     *
     * @param context The current context
     * @param finishCurrent If true finish the current activity
     */
    fun launch(
        context: Context,
        finishCurrent: Boolean = false,
        withTransition: Boolean = false,
        vararg elements: Pair<View, String> = emptyArray()
    ) {
        val intent = intent(context)
        if (withTransition) {
            if (context is Activity) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, *elements)
                context.startActivity(intent, options.toBundle())
            }
        } else {
            context.startActivity(intent)
        }
        if (finishCurrent) {
            (context as AppCompatActivity).finish()
        }
    }
}