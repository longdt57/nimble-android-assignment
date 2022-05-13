package co.nimble.lee.assignment.data.service.token_helper

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

internal fun appendTokenType(request: Request, tokenType: String): Request {
    var accessToken = request.getHeaderAuthorization()
    return if (accessToken == null || accessToken.startsWith(tokenType))
        request
    else {
        accessToken = "$tokenType $accessToken"
        updateRequestWithNewAccessToken(request, accessToken)
    }
}

internal fun updateRequestWithNewAccessToken(request: Request, newAccessToken: String): Request {
    return request.newBuilder()
        .removeHeader(HEADER_AUTHORIZATION)
        .addHeader(HEADER_AUTHORIZATION, newAccessToken)
        .build()
}

internal fun Request.getHeaderAuthorization(): String? = header(HEADER_AUTHORIZATION)

internal fun Response.isUnauthorized() = code == HTTP_ERROR_CODE_UNAUTHORIZED

internal fun Request.hasNewAccessToken(localAccessToken: String): Boolean {
    return getHeaderAuthorization().orEmpty().contains(localAccessToken).not()
}

internal fun TokenStorage.isLocalAccessTokenValid(): Boolean {
    val expiredTime = createdAt + expireIn
    val isExpired = expiredTime * 1000 <= System.currentTimeMillis()

    return accessToken.isNotBlank() && isExpired.not()
}
