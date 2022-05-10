package co.nimble.lee.assignment.data.request


import co.nimble.lee.assignment.data.BuildConfig
import com.squareup.moshi.Json

data class SignInRequest(
    @Json(name="email")
    val email: String,
    @Json(name="password")
    val password: String,
    @Json(name="client_id")
    val clientId: String? = BuildConfig.API_KEY,
    @Json(name="client_secret")
    val clientSecret: String? = BuildConfig.API_SECRET,
    @Json(name="grant_type")
    val grantType: String? = GRAND_TYPE_PASSWORD
)