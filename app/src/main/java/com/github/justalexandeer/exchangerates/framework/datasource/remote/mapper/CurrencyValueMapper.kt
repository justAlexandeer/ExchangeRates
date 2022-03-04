package com.github.justalexandeer.exchangerates.framework.datasource.remote.mapper

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.framework.datasource.remote.response.CurrencyValueResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyValueMapper @Inject constructor() {
    fun fromCurrencyValueResponseToCurrencyValue(
        currencyValueResponse: CurrencyValueResponse
    ): List<CurrencyValue> {
        val listCurrencyValue = mutableListOf<CurrencyValue>()
        currencyValueResponse.results.forEach {
            listCurrencyValue.add(CurrencyValue(it.key, it.value))
        }
        return listCurrencyValue
    }
}