package co.nimble.lee.assignment.data.service.tokenhelper

import android.content.Context
import android.content.Intent
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storage: TokenStorage,
    private val refreshTokenRepository: RefreshTokenHelper,
    @LogoutServiceIntent private val logoutServiceIntent: Intent
) : Interceptor {

    private val tokenType: String
        get() = storage.tokenType

    private val localAccessToken: String
        get() = storage.accessToken

    private val localRefreshToken: String
        get() = storage.refreshToken

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = updateRequestHeaderAuthorization(chain.request())

            val response = if (storage.isLocalAccessTokenValid()) {
                handleValidAccessToken(request, chain)
            } else {
                handleInvalidAccessToken(request, chain)
            }

            // Not Call API if token is invalid
            return response ?: throw IllegalArgumentException("Invalid Token")
        } catch (t: Throwable) {
            throw IOException("SafeGuarded when requesting ${chain.request().url}", t)
        }
    }

    private fun handleValidAccessToken(request: Request, chain: Interceptor.Chain): Response? {
        val response = chain.proceed(request)
        return onValidAccessTokenResponse(request, chain, response)
    }

    /**
     * Handle Response.
     * Check if error isUnauthorized, refresh token
     * Else return response
     */
    private fun onValidAccessTokenResponse(
        request: Request,
        chain: Interceptor.Chain,
        response: Response,
    ): Response? {
        return when {
            response.isUnauthorized() -> handleInvalidAccessToken(request, chain)
            else -> response
        }
    }

    /**
     * Refresh Token, then recall the api
     */
    private fun handleInvalidAccessToken(request: Request, chain: Interceptor.Chain): Response? {
        // If refreshToken api is calling, await for it
        val newRequest: Request = refreshTokenAndUpdateRequest(request) ?: return null
        return chain.proceed(newRequest)
    }

    /**
     * Refresh Token. Log out if refresh token fail
     */
    private fun refreshTokenAndUpdateRequest(request: Request): Request? {
        synchronized(this) {
            // If another request already executed this block and refresh token successfully.
            // Just return request with new access token
            if (storage.isLocalAccessTokenValid() && request.isLocalAccessTokenUpdated(localAccessToken)) {
                return updateRequestHeaderAuthorization(request)
            }

            return if (AuthTokenUtils.isValidRefreshToken(localRefreshToken)) {
                try {
                    runBlocking {
                        refreshTokenRepository.refreshAndSaveToken()
                        updateRequestHeaderAuthorization(request)
                    }
                } catch (e: Exception) {
                    if (isInvalidRefreshTokenException(e)) {
                        logout()
                    }
                    null
                }
            } else {
                logout()
                null
            }
        }
    }

    private fun isInvalidRefreshTokenException(e: Exception): Boolean {
        if (e !is HttpException) return false
        return e.code() in listOf(HTTP_ERROR_CODE_UNAUTHORIZED, HTTP_BAD_REQUEST)
    }

    private fun updateRequestHeaderAuthorization(request: Request): Request {
        val authorizationHeader = "$tokenType $localAccessToken"
        return request.newBuilder()
            .removeHeader(HEADER_AUTHORIZATION)
            .addHeader(HEADER_AUTHORIZATION, authorizationHeader)
            .build()
    }

    private fun logout() {
        context.startService(logoutServiceIntent)
    }
}
