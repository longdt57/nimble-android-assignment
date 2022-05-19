package co.nimblehq.coroutine.mockrepository

import co.nimble.lee.assignment.domain.model.Message
import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.repository.OAuthRepository

class MockOAuthRepository: OAuthRepository {

    private var shouldReturnError = false
    private var isLogin = false

    override suspend fun signInWithEmail(email: String, password: String): TokenInfo {
        if (shouldReturnError) throw Throwable("")
        else return TokenInfo(
            id = "id",
            type = "token",
            accessToken = "lbxD2K2BjbYtNzz8xjvh2FvSKx838KBCf79q773kq2c",
            tokenType = "Bearer",
            expiresIn = 7200,
            refreshToken = "3zJz2oW0njxlj_I3ghyUBF7ZfdQKYXd2n0ODlMkAjHc",
            createdAt = 1597169495
        )
    }

    override suspend fun saveAuthToken(tokenInfo: TokenInfo) {

    }

    override suspend fun forgotPassword(email: String): Message? {
        if (shouldReturnError) throw Throwable("")
        else return Message()
    }

    override fun isLoggedIn(): Boolean {
        return isLogin
    }
}
