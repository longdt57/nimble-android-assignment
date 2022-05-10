package co.nimblehq.coroutine.data.repository

import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.data.request.SignInRequest
import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.SignInResponse
import co.nimble.lee.assignment.data.response.toTokenInfo
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.service.AuthenticatedApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var authenticatedApiService: AuthenticatedApiService
    private lateinit var tokenStorage: TokenStorage
    private lateinit var repository: UserRepository

    private val signInRequest = SignInRequest(
        email = "dev@nimblehq.co",
        password = "12345678"
    )

    private val signInResponse = SignInResponse(
        id = "id",
        type = "token",
        attributes = SignInResponse.Attributes(
            accessToken = "lbxD2K2BjbYtNzz8xjvh2FvSKx838KBCf79q773kq2c",
            tokenType = "Bearer",
            expiresIn = 7200,
            refreshToken = "3zJz2oW0njxlj_I3ghyUBF7ZfdQKYXd2n0ODlMkAjHc",
            createdAt = 1597169495
        )
    )

    @Before
    fun setup() {
        mockService = mockk()
        authenticatedApiService = mockk()
        tokenStorage = mockk()
        repository = UserRepositoryImpl(mockService, authenticatedApiService, tokenStorage)
    }

    @Test
    fun `When calling sign in request successfully, it returns success response`() = runBlockingTest {
        coEvery {
            mockService.signInWithEmail(signInRequest)
        } returns ObjectItem(signInResponse)

        repository.signInWithEmail(email = signInRequest.email, signInRequest.password) shouldBe signInResponse.toTokenInfo()
    }

    @Test
    fun `When calling sign in request failed, it returns wrapped error`() = runBlockingTest {
        coEvery { mockService.signInWithEmail(signInRequest) } throws Throwable()

        shouldThrow<Throwable> {
            repository.signInWithEmail(signInRequest.email, signInRequest.password)
        }
    }
}
