package co.nimble.lee.assignment.data.storage.local

import co.nimble.lee.assignment.data.storage.EncryptedSharedPreferences
import co.nimble.lee.assignment.domain.model.TokenInfo
import javax.inject.Inject

interface ITokenStorage {
    fun saveTokenInfo(tokenInfo: TokenInfo)
    fun clearTokenInfo()
}

class TokenStorage @Inject constructor(preference: EncryptedSharedPreferences) : ITokenStorage {

    var accessToken: String by preference.preferences(PREF_KEY_ACCESS_TOKEN, "")
    var refreshToken: String by preference.preferences(PREF_KEY_REFRESH_TOKEN, "")
    var tokenType: String by preference.preferences(PREF_KEY_TOKEN_TYPE, DEFAULT_TOKEN_TYPE)
    var expireIn: Long by preference.preferences(PREF_KEY_TOKEN_EXPIRE_IN, 0L)
    var createdAt: Long by preference.preferences(PREF_KEY_TOKEN_CREATE_AT, 0)

    override fun saveTokenInfo(tokenInfo: TokenInfo) {
        accessToken = tokenInfo.accessToken.orEmpty()
        refreshToken = tokenInfo.refreshToken.orEmpty()
        expireIn = tokenInfo.expiresIn ?: 0
        tokenType = tokenInfo.tokenType ?: DEFAULT_TOKEN_TYPE
        createdAt = tokenInfo.createdAt ?: 0
    }

    override fun clearTokenInfo() {
        accessToken = ""
        refreshToken = ""
        expireIn = 0
        tokenType = ""
        createdAt = 0
    }

}