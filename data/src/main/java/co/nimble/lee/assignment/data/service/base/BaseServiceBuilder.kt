package co.nimble.lee.assignment.data.service.base

import co.nimble.lee.assignment.data.BuildConfig
import co.nimble.lee.assignment.data.service.base.RetrofitConst.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

open class BaseServiceBuilder @Inject constructor() {

    protected val headers: MutableMap<String, String> = mutableMapOf()
    private val interceptors: MutableList<Interceptor> = ArrayList()
    protected val clientBuilder: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

    fun <T> create(clazz: Class<T>, endpoint: String): T {
        return createRetrofitBuilder(endpoint).build().create(clazz)
    }

    open fun createRetrofitBuilder(endpoint: String): Retrofit.Builder {
        return build(endpoint)
    }

    fun addHeader(key: String, value: String): BaseServiceBuilder {
        headers[key] = value
        return this
    }

    fun addInterceptor(interceptor: Interceptor): BaseServiceBuilder {
        interceptors.add(interceptor)
        return this
    }

    fun addAccessToken(accessToken: String): BaseServiceBuilder {
        addHeader(ACCESS_TOKEN, accessToken)
        return this
    }

    fun build(endpoint: String): Retrofit.Builder {
        clientBuilder.addHeaders(headers)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(clientBuilder.build())
    }
}
