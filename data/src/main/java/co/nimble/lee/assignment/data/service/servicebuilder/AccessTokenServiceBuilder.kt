package co.nimble.lee.assignment.data.service.servicebuilder

import co.nimble.lee.assignment.data.service.base.BaseServiceBuilder
import co.nimble.lee.assignment.data.service.tokenhelper.TokenInterceptor
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import retrofit2.Converter
import javax.inject.Inject

class AccessTokenServiceBuilder @Inject constructor(
    tokenStorage: TokenStorage,
    tokenInterceptor: TokenInterceptor,
    converterFactory: Converter.Factory
) : BaseServiceBuilder() {

    init {
        addAccessToken("${tokenStorage.tokenType} ${tokenStorage.accessToken}")
        addInterceptor(tokenInterceptor)
        addConverterFactory(converterFactory)
    }
}