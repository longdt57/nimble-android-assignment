package co.nimble.lee.assignment.data.request


import co.nimble.lee.assignment.data.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SignInRequest(
    @Json(name="client_id")
    val clientId: String? = BuildConfig.API_KEY,
    @Json(name="client_secret")
    val clientSecret: String? = BuildConfig.API_SECRET,
    @Json(name="email")
    val email: String? = null,
    @Json(name="grant_type")
    val grantType: String? = GRAND_TYPE_PW,
    @Json(name="password")
    val password: String? = null
)