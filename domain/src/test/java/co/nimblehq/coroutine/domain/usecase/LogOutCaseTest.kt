package co.nimblehq.coroutine.domain.usecase

import co.nimble.lee.assignment.domain.repository.LogoutRepository
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LogOutCaseTest {

    private lateinit var mockRepository: LogoutRepository
    private lateinit var usecase: LogOutUseCase

    @Before
    fun setup() {
        mockRepository = mockk()
        usecase = LogOutUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockRepository.logout() } returns Unit

        usecase.invoke(Unit).run {
            (this as UseCaseResult.Success).data shouldBe Unit
        }
    }
}
