package co.nimble.lee.assignment.data.service.token_helper

import co.nimble.lee.assignment.data.request.RefreshTokenRequest
import co.nimble.lee.assignment.data.response.toTokenInfo
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.storage.local.ITokenStorage
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.model.TokenInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenHelper @Inject constructor(
    private val storage: TokenStorage,
    private val service: OAuthApiService
) : ITokenStorage by storage {

    @Throws(NullPointerException::class)
    fun refreshAndSaveToken() {
        refreshToken().apply {
            storage.saveTokenInfo(this)
        }
    }

    @Throws(NullPointerException::class)
    fun refreshToken(): TokenInfo {
        return service.refreshToken(RefreshTokenRequest(refreshToken = storage.refreshToken))
            .body()!!.data!!.toTokenInfo()
    }

}