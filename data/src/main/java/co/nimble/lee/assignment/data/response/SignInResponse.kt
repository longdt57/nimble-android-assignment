package co.nimble.lee.assignment.data.response

import co.nimble.lee.assignment.domain.model.TokenInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    @Json(name = "attributes")
    val attributes: Attributes? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "type")
    val type: String? = null
) {
    data class Attributes(
        @Json(name = "access_token")
        val accessToken: String? = null,
        @Json(name = "created_at")
        val createdAt: Long? = null,
        @Json(name = "expires_in")
        val expiresIn: Long? = null,
        @Json(name = "refresh_token")
        val refreshToken: String? = null,
        @Json(name = "token_type")
        val tokenType: String? = null
    )
}

fun SignInResponse.toTokenInfo(): TokenInfo = TokenInfo(
    id = id,
    type = type,
    accessToken = attributes?.accessToken,
    refreshToken = attributes?.refreshToken,
    expiresIn = attributes?.expiresIn,
    createdAt = attributes?.createdAt,
    tokenType = attributes?.tokenType
)