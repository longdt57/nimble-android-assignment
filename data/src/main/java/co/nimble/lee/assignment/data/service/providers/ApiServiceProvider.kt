package co.nimble.lee.assignment.data.service.providers

import co.nimble.lee.assignment.data.service.OAuthApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): OAuthApiService {
        return retrofit.create(OAuthApiService::class.java)
    }
}
