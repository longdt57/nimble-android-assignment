package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.database.AppDatabase
import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val apiService: OAuthApiService,
    private val appDatabase: AppDatabase,
    private val tokenStorage: TokenStorage
) : LogoutRepository {

    override suspend fun logout() {
        revokeToken()
        clearLocalTokenInfo()
        clearDatabase()
    }

    private suspend fun executeIgnoreException(execute: suspend () -> Unit) = try {
        execute.invoke()
    } catch (e: Exception) {
    }

    private suspend fun revokeToken() = executeIgnoreException {
        apiService.logout(LogoutRequest(token = tokenStorage.refreshToken))
    }

    private suspend fun clearLocalTokenInfo() = executeIgnoreException {
        tokenStorage.clearTokenInfo()
    }

    private suspend fun clearDatabase() = executeIgnoreException {
        appDatabase.clearAllTables()
    }
}