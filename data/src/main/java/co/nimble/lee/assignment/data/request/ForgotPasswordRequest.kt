package co.nimble.lee.assignment.data.request

import co.nimble.lee.assignment.data.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForgotPasswordRequest(
    @Json(name = "client_id")
    val clientId: String? = BuildConfig.API_KEY,
    @Json(name = "client_secret")
    val clientSecret: String? = BuildConfig.API_SECRET,
    @Json(name = "user")
    val user: User
) {
    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "email")
        val email: String? = null
    )

    constructor(email: String) : this(user = User(email))
}
