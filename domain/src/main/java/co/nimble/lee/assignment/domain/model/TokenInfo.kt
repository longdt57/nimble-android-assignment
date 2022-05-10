package co.nimble.lee.assignment.domain.model

data class TokenInfo(
    val id: String? = null,
    val type: String? = null,
    val accessToken: String? = null,
    val createdAt: Long? = null,
    val expiresIn: Long? = null,
    val refreshToken: String? = null,
    val tokenType: String? = null
)