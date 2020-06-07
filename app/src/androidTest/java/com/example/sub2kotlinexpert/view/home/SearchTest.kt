@file:Suppress("DEPRECATION")

package com.example.sub2kotlinexpert.view.home


import android.view.KeyEvent

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.sub2kotlinexpert.R.id.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class SearchTest {

    @Rule
    @JvmField var mainActivity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchTest() {

        onView(withId(search))
            .check(matches(isDisplayed()))
        onView(withId(search))
            .perform(click())
        onView(withId(search_match_fragment))
            .check(matches(isDisplayed()))
        onView(withId(search_src_text)).check(matches(isAssignableFrom(EditText::class.java)))
        onView(withId(search_src_text)).perform(typeText("arsenal"), pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(3000)
        onView(withId(rv_search_match)).check(matches(isDisplayed()))
        onView(withId(rv_search_match))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(detail_activity)).check(matches(isDisplayed()))
        Thread.sleep(1500)
        onView(isRoot()).perform(pressBack())

        onView(withId(search_close_btn)).check(matches(isDisplayed()))
        onView(withId(search_close_btn)).perform(click())
        onView(withId(search_src_text)).check(matches(isAssignableFrom(EditText::class.java)))
        onView(withId(search_src_text)).check(matches(withText("")))
        onView(withId(search_src_text)).perform(typeText("aaa"), pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(3000)


    }

}