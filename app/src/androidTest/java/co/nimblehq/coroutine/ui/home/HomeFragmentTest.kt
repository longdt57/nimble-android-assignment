package co.nimblehq.coroutine.ui.home

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.ui.screens.ext.getDateTimeEEMMdd
import co.nimble.lee.assignment.ui.screens.home.HomeFragment
import co.nimblehq.coroutine.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var surveyRepository: SurveyRepository

    @Before
    fun init() {
        hiltRule.inject()
        launchFragment()
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment> {
        }
    }

    @Test
    fun validateInitialUI() {
        onView(withId(R.id.tvDateTime))
            .check(matches(isDisplayed()))
            .check(matches(withText(getDateTimeEEMMdd(System.currentTimeMillis()).toString())))
    }

}