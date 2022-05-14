package co.nimblehq.coroutine.ui.screens.auth

import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentSignInBinding
import co.nimble.lee.assignment.ui.screens.auth.SignInFragment
import co.nimble.lee.assignment.ui.screens.auth.SignInViewModel
import co.nimblehq.coroutine.test.getPrivateProperty
import co.nimblehq.coroutine.test.replace
import co.nimblehq.coroutine.ui.BaseFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SignInFragmentTest : BaseFragmentTest<SignInFragment, FragmentSignInBinding>() {

    private val mockViewModel = mockk<SignInViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing SignInFragment, its views display the text correctly`() {
        launchFragment()
        fragment.binding.btnLogin.text.toString() shouldBe fragment.getString(R.string.label_login)
        fragment.binding.edtEmail.hint.toString() shouldBe fragment.getString(R.string.label_email)
        fragment.binding.edtPassword.hint.toString() shouldBe fragment.getString(R.string.label_password)
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<SignInFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
            }
        ) {
            fragment = this
        }
    }
}
