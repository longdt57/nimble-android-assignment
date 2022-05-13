package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.BuildConfig
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.service.providers.ApiServiceProvider
import co.nimble.lee.assignment.data.service.providers.ConverterFactoryProvider
import co.nimble.lee.assignment.data.service.providers.RetrofitProvider
import co.nimble.lee.assignment.data.service.servicebuilder.AccessTokenServiceBuilder
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

    @Provides
    fun provideOAuthApiService(retrofit: Retrofit): OAuthApiService =
        ApiServiceProvider.getApiService(retrofit)

    @Provides
    fun provideApiService(retrofit: AccessTokenServiceBuilder): ApiService {
        return retrofit.create(ApiService::class.java, provideBaseApiUrl())
    }
}
