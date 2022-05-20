package co.nimblehq.coroutine.ui.auth

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
import co.nimble.lee.assignment.ui.screens.auth.SignInFragment
import co.nimblehq.coroutine.launchFragmentInHiltContainer
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
class SignInFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    var context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun init() {
        hiltRule.inject()
        launchFragment()
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<SignInFragment> {
        }
    }

    @Test
    fun validateInitialUI() {
        onView(withId(R.id.ivLogo))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.edtEmail))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withHint(context.getString(R.string.label_email))))

        onView(withId(R.id.edtPassword))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withHint(context.getString(R.string.label_password))))

        onView(withId(R.id.tvForgot))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(context.getString(R.string.label_sign_in_forgot))))

        onView(withId(R.id.btnLogin))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(context.getString(R.string.label_login))))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun checkLoginButtonIsEnabled() {
        onView(withId(R.id.edtEmail))
            .perform(replaceText("dev@nimblehq.co"))
        onView(withId(R.id.edtPassword))
            .perform(replaceText("11111111"))

        onView(withId(R.id.btnLogin))
            .check(matches(isEnabled()))
    }
}