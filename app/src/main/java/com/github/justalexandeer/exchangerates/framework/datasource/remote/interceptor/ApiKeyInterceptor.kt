package com.github.justalexandeer.exchangerates.framework.datasource.remote.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url.newBuilder()
            .addQueryParameter(API_KEY_PARAMETER_NAME, API_KEY)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

private const val API_KEY_PARAMETER_NAME = "api_key"
private const val API_KEY = "85f7cda11c-5882582932-r869qe"