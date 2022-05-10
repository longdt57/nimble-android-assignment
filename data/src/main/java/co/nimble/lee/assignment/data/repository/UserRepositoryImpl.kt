package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.request.SignInRequest
import co.nimble.lee.assignment.data.response.toTokenInfo
import co.nimble.lee.assignment.data.response.toUsers
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.service.AuthenticatedApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService,
    private val authenticatedApiService: AuthenticatedApiService,
    private val tokenStorage: TokenStorage
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().toUsers()
    }

    @Throws(NullPointerException::class)
    override suspend fun signInWithEmail(email: String, password: String): TokenInfo {
        return apiService.signInWithEmail(
            SignInRequest(
                email = email,
                password = password
            )
        ).data!!.toTokenInfo()
    }

    override suspend fun saveAuthToken(tokenInfo: TokenInfo) {
        tokenStorage.saveTokenInfo(tokenInfo)
    }

    override suspend fun isLoggedIn(): Boolean = tokenStorage.accessToken.isNotBlank()

    override suspend fun logout() {
        try {
            // Todo update this Function
            authenticatedApiService.logout(LogoutRequest(token = tokenStorage.refreshToken))
        } finally {
            tokenStorage.clearTokenInfo()
        }
    }
}
