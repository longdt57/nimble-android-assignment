package co.nimble.lee.assignment.data.storage.local

import co.nimble.lee.assignment.data.storage.EncryptedSharedPreferences
import javax.inject.Inject

class TokenStorage @Inject constructor(preference: EncryptedSharedPreferences) {

    var accessToken: String? by preference.preferencesNullable(PREF_KEY_ACCESS_TOKEN)
    var refreshToken: String? by preference.preferencesNullable(PREF_KEY_REFRESH_TOKEN)
    var tokenType: String? by preference.preferencesNullable(PREF_KEY_TOKEN_TYPE)
    var expireIn: Long? by preference.preferencesNullable(PREF_KEY_TOKEN_EXPIRE_IN)
    var createdAt: Long? by preference.preferencesNullable(PREF_KEY_TOKEN_CREATE_AT)

}