package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.BuildConfig
import co.nimble.lee.assignment.data.service.token_helper.TokenInterceptor
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
    fun provideAuthenticatedOkHttpClient(authInterceptor: TokenInterceptor) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        addInterceptor(authInterceptor)
    }.build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedOkHttpClient
