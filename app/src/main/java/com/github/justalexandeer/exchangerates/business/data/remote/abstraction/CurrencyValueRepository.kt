package com.github.justalexandeer.exchangerates.business.data.remote.abstraction

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue

interface CurrencyValueRepository {
    suspend fun getAllCurrencyValue(base: String): List<CurrencyValue>
    suspend fun getMultiCurrenciesValue(base: String, multiCurrency: List<Currency>): List<CurrencyValue>
}