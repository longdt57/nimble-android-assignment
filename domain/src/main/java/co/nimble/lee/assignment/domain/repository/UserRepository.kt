package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
    suspend fun signInWithEmail(email: String, password: String): TokenInfo
    suspend fun saveAuthToken(tokenInfo: TokenInfo)
    suspend fun isLoggedIn(): Boolean
}
