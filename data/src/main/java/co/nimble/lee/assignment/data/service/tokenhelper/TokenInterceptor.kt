package co.nimble.lee.assignment.data.service.tokenhelper

import co.nimble.lee.assignment.data.storage.local.TokenStorage
import kotlinx.coroutines.runBlocking
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
        val response = chain.proceed(updateRequestHeaderAuthorization(request))
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
        val newResponse = chain.proceed(updateRequestHeaderAuthorization(newRequest))
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
                return updateRequestHeaderAuthorization(request)
            }

            return if (AuthTokenUtils.isValidRefreshToken(localRefreshToken)) {
                try {
                    runBlocking {
                        refreshTokenRepository.refreshAndSaveToken()
                        updateRequestHeaderAuthorization(request)
                    }
                } catch (e: Exception) {
                    // Todo should logout to prevent recall api
                    request
                }
            } else {
                // Todo should logout to prevent recall api
                request
            }
        }
    }

    private fun updateRequestHeaderAuthorization(request: Request): Request {
        val authorizationHeader = "$tokenType $localAccessToken"
        return request.newBuilder()
            .removeHeader(HEADER_AUTHORIZATION)
            .addHeader(HEADER_AUTHORIZATION, authorizationHeader)
            .build()
    }
}
