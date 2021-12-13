package com.example.favorite

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.favorite.di.FavoriteModule
import com.example.favorite.domain.GetFavoriteListUseCase
import com.example.favorite.presentation.FavoriteFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {

    @Before
    fun setupKoin() {
        startKoin {  }
        FavoriteModule().load()
        loadKoinModules(
            module(override = true) {
                factory<GetFavoriteListUseCase> { StubGetFavoriteListUseCase }
            }
        )
    }

    @After
    fun tearDownKoin() {
        stopKoin()
    }

    @Test
    fun whenFragmentIsStarted_shouldDisplayRecycerView() {
        launchFragmentInContainer<FavoriteFragment>(initialState = Lifecycle.State.STARTED)
        onView(withId(R.id.rvFavoriteStoresList)).check(matches(isDisplayed()))
    }

    @Test
    fun whenFragmentIsStarted_shouldDisplayRecyclerViewItemsCorrectly() {
        launchFragmentInContainer<FavoriteFragment>(initialState = Lifecycle.State.STARTED)

        onView(withId(R.id.rvFavoriteStoresList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Lojas Americanas"))
                )
            )

        onView(withId(R.id.rvFavoriteStoresList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Magalu"))
                )
            )
    }
}