package com.arctouch.codechallenge.utils

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.arctouch.codechallenge.base.presentation.views.CustomRecyclerView
import com.schibsted.spain.barista.internal.matcher.DisplayedMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher


fun assertCustomRecyclerViewItemCount(@IdRes customRecyclerViewId: Int, expectedItemCount: Int) {
    onView(DisplayedMatchers.displayedWithId(customRecyclerViewId))
        .check(CustomRecyclerViewItemCountAssertion(expectedItemCount))
}

class CustomRecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = (view as CustomRecyclerView).recyclerView
        val adapter = recyclerView.adapter
        val recyclerViewItemCount = adapter?.itemCount
        ViewMatchers.assertThat(recyclerViewItemCount, Matchers.`is`(expectedCount))
    }
}

fun clickRecyclerViewItem(customRecyclerViewId: Int, position: Int) {
    onView(withRecyclerView(customRecyclerViewId).atPosition(position))
        .perform(click())
}

fun withRecyclerView(customRecyclerViewId: Int): CustomRecyclerViewMatcher {
    return CustomRecyclerViewMatcher(customRecyclerViewId)
}

class CustomRecyclerViewMatcher(internal val customRecyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description) {
                val id = if (targetViewId == -1) customRecyclerViewId else targetViewId
                var idDescription = Integer.toString(id)
                if (this.resources != null) {
                    idDescription = try {
                        this.resources?.getResourceName(id) ?: ""
                    } catch (var4: Resources.NotFoundException) {
                        String.format("%s (resource name not found)", id)
                    }

                }

                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources

                if (childView == null) {
                    (view.rootView.findViewById<View>(customRecyclerViewId) as CustomRecyclerView?)
                        ?.recyclerView?.let { recyclerView ->
                        childView =
                                recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                    }
                }

                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    view === targetView
                }

            }
        }
    }
}