package co.nimblehq.coroutine.domain.usecase

import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository
import co.nimble.lee.assignment.domain.usecase.GetUserUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserUsersCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var usecase: GetUserUseCase

    private val user = User(
        id = "1",
        type = "normal",
        name = "name",
        email = "email",
        avatarUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_",
    )

    @Before
    fun setup() {
        mockRepository = mockk()
        usecase = GetUserUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response`() = runBlockingTest {
        val expected = user
        coEvery { mockRepository.getUser() } returns expected

        usecase.execute().run {
            (this as UseCaseResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `When calling request failed, it returns wrapped error`() = runBlockingTest {
        val expected = Exception()
        coEvery { mockRepository.getUser() } throws expected

        usecase.execute().run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }
    }
}
