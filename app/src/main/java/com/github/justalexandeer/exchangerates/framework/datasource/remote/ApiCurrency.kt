package com.github.justalexandeer.exchangerates.framework.datasource.remote

import com.github.justalexandeer.exchangerates.framework.datasource.remote.response.CurrencyNameResponse
import com.github.justalexandeer.exchangerates.framework.datasource.remote.response.CurrencyValueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCurrency {
    @GET("/currencies")
    suspend fun getAllCurrencies(): CurrencyNameResponse

    @GET("/fetch-all")
    suspend fun getAllCurrenciesValueFromBase(
        @Query("from") baseCurrencies: String
    ): CurrencyValueResponse

    @GET("fetch-multi")
    suspend fun getMultiCurrenciesValueFromBase(
        @Query("from") baseCurrencies: String,
        @Query("to") multiCurrency: List<String>
    ): CurrencyValueResponse
}