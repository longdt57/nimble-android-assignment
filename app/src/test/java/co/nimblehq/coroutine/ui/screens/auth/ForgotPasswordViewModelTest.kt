package co.nimblehq.coroutine.ui.screens.auth

import co.nimble.lee.assignment.domain.model.Message
import co.nimble.lee.assignment.domain.usecase.ForgotPasswordUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.ui.screens.auth.ForgotPasswordViewModel
import co.nimblehq.coroutine.ui.CoroutineTestRule
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ForgotPasswordViewModelTest {

    private lateinit var mockUseCase: ForgotPasswordUseCase
    private lateinit var viewModel: ForgotPasswordViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val param = ForgotPasswordUseCase.Param(
        "dev@nimblehq.co"
    )
    
    @Before
    fun setup() {
        mockUseCase = mockk()
        viewModel = ForgotPasswordViewModel(
            coroutineTestRule.providerImplTest,
            mockUseCase
        )
    }

    @Test
    fun `When calling sign in, it returns success response`() = runTest(coroutineTestRule.providerImplTest.io) {
        val successMessage = Message("If your email address exists in our database, you will receive a password recovery link at your email address in a few minutes.")
        coEvery { mockUseCase.invoke(param) } returns UseCaseResult.Success(successMessage)

        val result = mutableListOf<String>()
        val job = launch { viewModel.requestSuccess.toList(result)}
        viewModel.forgotPassword(param.email)

        result.size shouldBe 1
        result.first() shouldBe successMessage.message
        job.cancel()
    }

    @Test
    fun `When calling sign in, it returns fail response`() = runTest(coroutineTestRule.providerImplTest.io) {
        val errorMessage = "Lee handsome"
        coEvery { mockUseCase.invoke(param) } returns UseCaseResult.Error(Throwable(message = errorMessage))

        val result = mutableListOf<String>()
        val job = launch { viewModel.error.toList(result) shouldBe Unit }
        viewModel.forgotPassword(param.email)

        result.size shouldBe 1
        result.first() shouldBe errorMessage
        job.cancel()
    }

}
