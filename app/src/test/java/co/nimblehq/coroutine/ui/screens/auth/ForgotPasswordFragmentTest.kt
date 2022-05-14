package co.nimblehq.coroutine.ui.screens.auth

import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentForgotPasswordBinding
import co.nimblehq.coroutine.ui.BaseFragmentTest
import co.nimble.lee.assignment.ui.screens.auth.ForgotPasswordFragment
import co.nimble.lee.assignment.ui.screens.auth.ForgotPasswordViewModel
import co.nimblehq.coroutine.test.getPrivateProperty
import co.nimblehq.coroutine.test.replace
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ForgotPasswordFragmentTest : BaseFragmentTest<ForgotPasswordFragment, FragmentForgotPasswordBinding>() {

    private val mockViewModel = mockk<ForgotPasswordViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing ForgotPasswordFragment, its views display the text correctly`() {
        launchFragment()
        fragment.binding.edtEmail.hint.toString() shouldBe fragment.getString(R.string.label_email)
        fragment.binding.btnDone.text.toString() shouldBe fragment.getString(R.string.reset)
        fragment.binding.tvDescription.text.toString() shouldBe fragment.getString(R.string.forgot_password_description)
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<ForgotPasswordFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
            }
        ) {
            fragment = this
        }
    }
}
