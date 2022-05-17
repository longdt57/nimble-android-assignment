package co.nimble.lee.assignment.data.service.servicebuilder

import co.nimble.lee.assignment.data.service.base.BaseServiceBuilder
import co.nimble.lee.assignment.data.service.tokenhelper.TokenInterceptor
import retrofit2.Converter
import javax.inject.Inject

class AccessTokenServiceBuilder @Inject constructor(
    tokenInterceptor: TokenInterceptor,
    converterFactory: Converter.Factory
) : BaseServiceBuilder() {

    init {
        addInterceptor(tokenInterceptor)
        addConverterFactory(converterFactory)
    }
}