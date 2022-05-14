package co.nimblehq.coroutine.data.repository

import co.nimble.lee.assignment.data.database.AppDatabase
import co.nimble.lee.assignment.data.repository.LogoutRepositoryImpl
import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.response.EmptyResponse
import co.nimble.lee.assignment.data.response.base.ObjectItem
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class LogoutRepositoryTest {

    private lateinit var mockService: OAuthApiService
    private lateinit var mockTokenStorage: TokenStorage
    private lateinit var mockDatabase: AppDatabase
    private lateinit var repository: LogoutRepository

    @Before
    fun setup() {
        mockService = mockk()
        mockTokenStorage = mockk()
        mockDatabase = mockk()
        repository = LogoutRepositoryImpl(mockService, mockDatabase, mockTokenStorage)
    }

    @Test
    fun `When calling logout request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockService.logout(LogoutRequest(token = anyString())) } returns ObjectItem(data = EmptyResponse())
        coEvery { mockDatabase.clearAllTables() } returns Unit
        coEvery { mockTokenStorage.clearTokenInfo() } returns Unit

        repository.logout().run {
            this shouldBe Unit
        }
    }

    @Test
    fun `When calling logout request failed, it returns Unit`() = runBlockingTest {
        coEvery { mockService.logout(LogoutRequest(anyString())) } throws Throwable()

        repository.logout() shouldBe Unit
    }

}
