package co.nimble.lee.assignment.data.response

import co.nimble.lee.assignment.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "attributes")
    val attributes: Attributes? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "type")
    val type: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Attributes(
        @Json(name = "avatar_url")
        val avatarUrl: String? = null,
        @Json(name = "email")
        val email: String? = null,
        @Json(name = "name")
        val name: String? = null
    )
}

fun UserResponse.toUser(): User {
    return User(
        id = id,
        type = type,
        name = attributes?.name,
        avatarUrl = attributes?.avatarUrl,
        email = attributes?.email
    )
}
