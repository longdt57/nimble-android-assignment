package co.nimblehq.coroutines.ui.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentSignInBinding
import co.nimble.lee.assignment.ui.screens.auth.AuthNavigator
import co.nimble.lee.assignment.ui.screens.auth.SignInFragment
import co.nimblehq.coroutines.BaseFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInFragmentTest : BaseFragmentTest<SignInFragment, FragmentSignInBinding>() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragment()
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<SignInFragment>(
            onInstantiate = {}
        ) {
            fragment = this
        }
    }

    @Test
    fun validateInitialUI() {
        onView(withId(R.id.edtEmail))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withHint(fragment.getString(R.string.label_email))))

        onView(withId(R.id.edtPassword))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withHint(fragment.getString(R.string.label_password))))

        onView(withId(R.id.tvForgot))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(fragment.getString(R.string.label_password))))

        onView(withId(R.id.btnLogin))
            .check(matches(ViewMatchers.isDisplayed()))
            .check(matches(withText(fragment.getString(R.string.label_login))))
            .check(matches(isNotEnabled()))
    }
}