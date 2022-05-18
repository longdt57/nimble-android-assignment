package co.nimblehq.coroutine.ui.screens.auth

import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.usecase.SignInUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.ui.screens.auth.SignInViewModel
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
class SignInViewModelTest {

    private lateinit var mockUseCase: SignInUseCase
    private lateinit var viewModel: SignInViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val param = SignInUseCase.Param(
        "dev@nimblehq.co",
        "12345678"
    )

    private val tokenInfo = TokenInfo(
        id = "id",
        type = "token",
        accessToken = "lbxD2K2BjbYtNzz8xjvh2FvSKx838KBCf79q773kq2c",
        tokenType = "Bearer",
        expiresIn = 7200,
        refreshToken = "3zJz2oW0njxlj_I3ghyUBF7ZfdQKYXd2n0ODlMkAjHc",
        createdAt = 1597169495
    )

    @Before
    fun setup() {
        mockUseCase = mockk()
        viewModel = SignInViewModel(
            coroutineTestRule.providerImplTest,
            mockUseCase
        )
    }

    @Test
    fun `When calling sign in, it returns success response`() = runTest(coroutineTestRule.providerImplTest.io) {
        coEvery { mockUseCase.invoke(param) } returns UseCaseResult.Success(tokenInfo)

        val result = mutableListOf<Unit>()
        val job = launch { viewModel.signInSuccess.toList(result) }
        viewModel.signInWithEmail(param.email, param.password)

        result.size shouldBe 1
        result.first() shouldBe Unit
        job.cancel()
    }

    @Test
    fun `When calling sign in, it returns fail response`() = runTest(coroutineTestRule.providerImplTest.io) {
        val errorMessage = "Lee handsome"
        coEvery { mockUseCase.invoke(param) } returns UseCaseResult.Error(Throwable(message = errorMessage))

        val result = mutableListOf<String>()
        val job = launch { viewModel.error.toList(result)}
        viewModel.signInWithEmail(param.email, param.password)

        result.size shouldBe 1
        result.first() shouldBe errorMessage
        job.cancel()
    }

}
