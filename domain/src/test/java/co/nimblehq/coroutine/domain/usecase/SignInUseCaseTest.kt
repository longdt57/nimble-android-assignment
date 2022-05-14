package co.nimblehq.coroutine.domain.usecase

import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.domain.usecase.SignInUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInUseCaseTest {

    private lateinit var mockRepository: OAuthRepository
    private lateinit var usecase: SignInUseCase

    private val param = SignInUseCase.Param(
        email = "dev@nimblehq.co",
        password = "12345678"
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
        mockRepository = mockk()
        usecase = SignInUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockRepository.signInWithEmail(param.email, param.password) } returns tokenInfo
        coEvery { mockRepository.saveAuthToken(tokenInfo) } returns Unit

        usecase.invoke(param).run {
            (this as UseCaseResult.Success).data shouldBe tokenInfo
        }

        io.mockk.verify(exactly = 1) {
            runBlockingTest { mockRepository.saveAuthToken(tokenInfo) }
        }
    }

    @Test
    fun `When calling request failed, it returns wrapped error`() = runBlockingTest {
        val expected = Exception()
        coEvery { mockRepository.signInWithEmail(any(), any()) } throws expected
        coEvery { mockRepository.saveAuthToken(any()) } returns Unit

        usecase.invoke(param).run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }

        io.mockk.verify(exactly = 0) {
            runBlockingTest { mockRepository.saveAuthToken(any()) }
        }
    }
}
