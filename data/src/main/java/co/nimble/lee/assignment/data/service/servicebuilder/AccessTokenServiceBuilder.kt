package co.nimble.lee.assignment.data.service.servicebuilder

import co.nimble.lee.assignment.data.service.base.BaseServiceBuilder
import co.nimble.lee.assignment.data.service.token_helper.TokenInterceptor
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import javax.inject.Inject

class AccessTokenServiceBuilder @Inject constructor(
    tokenStorage: TokenStorage,
    tokenInterceptor: TokenInterceptor
) : BaseServiceBuilder() {

    init {
        addAccessToken("${tokenStorage.tokenType} ${tokenStorage.accessToken}")
        addInterceptor(tokenInterceptor)
    }
}