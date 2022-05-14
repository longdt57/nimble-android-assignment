package co.nimble.lee.assignment.data.request


import co.nimble.lee.assignment.data.BuildConfig
import com.squareup.moshi.Json

data class RefreshTokenRequest(
    @Json(name="refresh_token")
    val refreshToken: String,
    @Json(name="grant_type")
    val grantType: String? = GRAND_TYPE_REFRESH_TOKEN,
    @Json(name="client_id")
    val clientId: String? = BuildConfig.API_KEY,
    @Json(name="client_secret")
    val clientSecret: String? = BuildConfig.API_SECRET
)