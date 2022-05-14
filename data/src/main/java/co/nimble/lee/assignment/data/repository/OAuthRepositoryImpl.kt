package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.request.ForgotPasswordRequest
import co.nimble.lee.assignment.data.request.SignInRequest
import co.nimble.lee.assignment.data.response.base.toMessage
import co.nimble.lee.assignment.data.response.toTokenInfo
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val apiService: OAuthApiService,
    private val tokenStorage: TokenStorage
) : OAuthRepository {

    @Throws(NullPointerException::class)
    override suspend fun signInWithEmail(email: String, password: String): TokenInfo {
        return apiService.signInWithEmail(
            SignInRequest(
                email = email,
                password = password
            )
        ).data!!.toTokenInfo()
    }

    override suspend fun forgotPassword(email: String) =
        apiService.forgotPassword(ForgotPasswordRequest(email)).meta?.toMessage()

    override suspend fun saveAuthToken(tokenInfo: TokenInfo) {
        tokenStorage.saveTokenInfo(tokenInfo)
    }

    override fun isLoggedIn(): Boolean = tokenStorage.accessToken.isNotBlank()

}
