package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.Message
import co.nimble.lee.assignment.domain.model.TokenInfo

interface OAuthRepository {

    suspend fun signInWithEmail(email: String, password: String): TokenInfo
    suspend fun saveAuthToken(tokenInfo: TokenInfo)

    suspend fun logout()
    fun isLoggedIn(): Boolean
    suspend fun forgotPassword(email: String): Message?
}
