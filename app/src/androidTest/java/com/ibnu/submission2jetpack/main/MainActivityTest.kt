package com.ibnu.submission2jetpack.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.utils.DataDummy
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    private val dummyDataMovie = DataDummy().dataMovie()
    private val dummyDataTv = DataDummy().dataTVShow()
    private val dummyDataPeople = DataDummy().dataPeople()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyDataMovie.size))
    }

    @Test
    fun loadMoviesDetail() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_detail_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail_movie)).check(matches(withText(dummyDataMovie[0].movieName)))
        onView(withId(R.id.tv_description_detail_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description_detail_movie)).check(matches(withText(dummyDataMovie[0].movieDescription)))
        onView(withId(R.id.tv_rating_detail_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail_movie)).check(matches(withText(dummyDataMovie[0].movieAverageRating)))
    }

    @Test
    fun loadTvShows() {
        onView(allOf(withText("TV Shows"), withEffectiveVisibility(Visibility.VISIBLE))).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyDataTv.size))
    }

    @Test
    fun loadTvShowDetail() {
        onView(allOf(withText("TV Shows"), withEffectiveVisibility(Visibility.VISIBLE))).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_detail_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail_tv_show)).check(matches(withText(dummyDataTv[0].tvName)))
        onView(withId(R.id.tv_description_detail_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description_detail_tv_show)).check(matches(withText(dummyDataTv[0].tvDescription)))
        onView(withId(R.id.tv_rating_detail_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail_tv_show)).check(matches(withText(dummyDataTv[0].tvAverageRating)))
    }

    @Test
    fun loadPeople(){
        onView(allOf(withText("People"), withEffectiveVisibility(Visibility.VISIBLE))).perform(click())
        onView(withId(R.id.rv_people)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_people)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyDataPeople.size))
    }

    @Test
    fun loadFavorite() {
        onView(allOf(withText("Favorite"), withEffectiveVisibility(Visibility.VISIBLE))).perform(click())
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyDataPeople.size))
    }

}