package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.BuildConfig
import co.nimble.lee.assignment.data.service.token_helper.TokenInterceptor
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    @Provides
    @AuthenticatedOkHttpClient
    fun provideAuthenticatedOkHttpClient(
        authInterceptor: TokenInterceptor,
        tokenStorage: TokenStorage
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        addInterceptor(authInterceptor)
        addAccessTokenHeader(tokenType = tokenStorage.tokenType, accessToken = tokenStorage.accessToken)
    }.build()

    private fun OkHttpClient.Builder.addAccessTokenHeader(tokenType: String, accessToken: String) {
        addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()

            builder.addHeader(AUTHORIZATION, "$tokenType $accessToken")

            builder.method(original.method, original.body)
            chain.proceed(builder.build())
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedOkHttpClient
