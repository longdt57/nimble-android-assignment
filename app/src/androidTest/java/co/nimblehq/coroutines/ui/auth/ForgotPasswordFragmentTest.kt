package co.nimblehq.coroutines.ui.auth

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.ui.screens.auth.ForgotPasswordFragment
import co.nimblehq.coroutines.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ForgotPasswordFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    var context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun init() {
        hiltRule.inject()
        launchFragment()
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<ForgotPasswordFragment> {}
    }

    @Test
    fun validateInitialUI() {
        onView(withId(R.id.tvDescription))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(context.getString(R.string.forgot_password_description))))

        onView(withId(R.id.edtEmail))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withHint(context.getString(R.string.label_email))))

        onView(withId(R.id.btnDone))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(context.getString(R.string.reset))))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun checkLoginButtonIsEnabled() {
        onView(withId(R.id.edtEmail))
            .perform(replaceText("dev@nimblehq.co"))

        onView(withId(R.id.btnDone))
            .check(matches(isEnabled()))
    }
}