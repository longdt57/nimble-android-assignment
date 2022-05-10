package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.BuildConfig
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.service.AuthenticatedApiService
import co.nimble.lee.assignment.data.service.providers.ApiServiceProvider
import co.nimble.lee.assignment.data.service.providers.ConverterFactoryProvider
import co.nimble.lee.assignment.data.service.providers.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @AuthenticatedRetrofit
    @Provides
    fun provideVerifiedRetrofit(
        baseUrl: String,
        @AuthenticatedOkHttpClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        ApiServiceProvider.getApiService(retrofit)

    @Provides
    fun provideAuthenticatedApiService(@AuthenticatedRetrofit retrofit: Retrofit): AuthenticatedApiService {
        return retrofit.create(AuthenticatedApiService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedRetrofit