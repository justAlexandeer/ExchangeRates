package com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.framework.datasource.remote.ApiCurrency
import com.github.justalexandeer.exchangerates.framework.datasource.remote.mapper.CurrencyValueMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyValueRepositoryImpl @Inject constructor(
    private val apiCurrency: ApiCurrency,
    private val currencyValueMapper: CurrencyValueMapper
) : CurrencyValueRepository {
    override suspend fun getAllCurrencyValue(base: String): List<CurrencyValue> {
        return currencyValueMapper.fromCurrencyValueResponseToCurrencyValue(
            apiCurrency.getAllCurrenciesValueFromBase(
                base
            )
        )
    }

    override suspend fun getMultiCurrenciesValue(
        base: String,
        multiCurrency: List<Currency>
    ): List<CurrencyValue> {
        val multiCurrencyName = mutableListOf<String>()
        multiCurrency.forEach {
            multiCurrencyName.add(it.name)
        }
        return currencyValueMapper.fromCurrencyValueResponseToCurrencyValue(
            apiCurrency.getMultiCurrenciesValueFromBase(base, multiCurrencyName.toString())
        )
    }
}