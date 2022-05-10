package co.nimble.lee.assignment.data.service.token_helper

import co.nimble.lee.assignment.data.storage.local.TokenStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val storage: TokenStorage,
    private val refreshTokenRepository: RefreshTokenHelper
) : Interceptor {

    private val tokenType: String
        get() = storage.tokenType

    private val localAccessToken: String
        get() = storage.accessToken

    private val localRefreshToken: String
        get() = storage.refreshToken

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()

            return if (storage.isLocalAccessTokenValid()) {
                handleValidAccessToken(request, chain)
            } else {
                handleInvalidAccessToken(request, chain)
            }
        } catch (t: Throwable) {
            throw IOException("SafeGuarded when requesting ${chain.request().url}", t)
        }
    }

    private fun handleValidAccessToken(request: Request, chain: Interceptor.Chain): Response {
        val response = chain.proceed(appendTokenType(request, tokenType))
        return onValidAccessTokenResponse(request, chain, response)
    }

    /**
     * Handle Response.
     * Check if error 401, refresh token
     * Else return response
     */
    private fun onValidAccessTokenResponse(
        request: Request,
        chain: Interceptor.Chain,
        response: Response,
    ): Response {
        return when {
            response.isUnauthorized() -> handleInvalidAccessToken(request, chain)
            else -> response
        }
    }

    /**
     * Refresh Token, then recall the api
     */
    private fun handleInvalidAccessToken(request: Request, chain: Interceptor.Chain): Response {
        // If refreshToken api is calling, await for it
        val newRequest: Request = refreshTokenAndUpdateRequest(request)
        val newResponse = chain.proceed(appendTokenType(newRequest, tokenType))
        return onValidAccessTokenResponse(newRequest, chain, newResponse)
    }

    /**
     * Refresh Token. Log out if refresh token fail
     */
    private fun refreshTokenAndUpdateRequest(request: Request): Request {
        synchronized(this) {
            // If another request already executed this block and refresh token successfully.
            // Just return request with new access token
            if (request.hasNewAccessToken(localAccessToken)) {
                return updateRequestWithNewAccessToken(request, localAccessToken)
            }

            return if (AuthTokenUtils.isValidRefreshToken(localRefreshToken)) {
                try {
                    refreshTokenRepository.refreshAndSaveToken()
                    updateRequestWithNewAccessToken(request, localAccessToken)
                } catch (e: Exception) {
                    request
                }
            } else {
                request
            }
        }
    }
}
