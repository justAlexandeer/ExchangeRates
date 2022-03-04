package com.github.justalexandeer.exchangerates.framework.datasource.remote.mapper

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName
import com.github.justalexandeer.exchangerates.framework.datasource.remote.response.CurrencyNameResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFullNameMapper @Inject constructor() {
    fun fromCurrencyNameResponseToListCurrencyFullName(
        currencyNameResponse: CurrencyNameResponse
    ): List<CurrencyFullName> {
        val listCurrencyFullName = mutableListOf<CurrencyFullName>()
        currencyNameResponse.currencies.forEach {
            listCurrencyFullName.add(CurrencyFullName(it.key, it.value))
        }
        return listCurrencyFullName
    }
}