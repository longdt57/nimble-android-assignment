package co.nimble.lee.assignment.data.request


import co.nimble.lee.assignment.data.BuildConfig
import com.squareup.moshi.Json

data class LogoutRequest(
    @Json(name="token")
    val token: String,
    @Json(name="client_id")
    val clientId: String? = BuildConfig.API_KEY,
    @Json(name="client_secret")
    val clientSecret: String? = BuildConfig.API_SECRET
)