package co.nimble.lee.assignment.data.service.tokenhelper

import co.nimble.lee.assignment.data.storage.local.TokenStorage
import okhttp3.Request
import okhttp3.Response

internal object AuthTokenUtils {

    fun isValidAccessToken(token: String?): Boolean {
        return token.isNullOrBlank().not()
    }

    fun isValidRefreshToken(token: String?): Boolean = token.isNullOrBlank().not()
}

internal const val HEADER_AUTHORIZATION = "Authorization"
internal const val HTTP_ERROR_CODE_UNAUTHORIZED = 401
internal const val HTTP_BAD_REQUEST = 400

internal fun Request.getHeaderAuthorization(): String? = header(HEADER_AUTHORIZATION)

internal fun Response.isUnauthorized() = code == HTTP_ERROR_CODE_UNAUTHORIZED

internal fun Request.isLocalAccessTokenUpdated(localAccessToken: String): Boolean {
    return getHeaderAuthorization().orEmpty().contains(localAccessToken).not()
}

internal fun TokenStorage.isLocalAccessTokenValid(): Boolean {
    val expiredTime = createdAt + expireIn
    val isExpired = expiredTime * 1000 <= System.currentTimeMillis()

    return accessToken.isNotBlank() && isExpired.not()
}
