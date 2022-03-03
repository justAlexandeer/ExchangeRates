package com.github.justalexandeer.exchangerates.framework.datasource.remote

import com.github.justalexandeer.exchangerates.framework.datasource.remote.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceCurrency @Inject constructor(
    apiKeyInterceptor: ApiKeyInterceptor
) {
    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
}

private const val BASE_URL = "https://api.fastforex.io/"