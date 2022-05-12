package co.nimblehq.coroutine.data.repository

import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.UserResponse
import co.nimble.lee.assignment.data.response.toUser
import co.nimble.lee.assignment.data.service.ApiService
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
    private lateinit var repository: UserRepository

    private val userResponse = UserResponse(
        id = "1",
        type = "normal",
        attributes = UserResponse.Attributes(
            name = "name",
            email = "email",
            avatarUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_",
        )
    )

    @Before
    fun setup() {
        mockService = mockk()
        repository = UserRepositoryImpl(mockService)
    }

    @Test
    fun `When calling getUsers request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockService.getUser() } returns ObjectItem(userResponse)

        repository.getUser() shouldBe userResponse.toUser()
    }

    @Test
    fun `When calling getUsers request failed, it returns wrapped error`() = runBlockingTest {
        coEvery { mockService.getUser() } throws Throwable()

        shouldThrow<Throwable> {
            repository.getUser()
        }
    }
}
