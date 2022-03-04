package com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.fake

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import org.junit.Assert.*

class FakeTestCurrencyValueRepository: CurrencyValueRepository {
    var data = mutableListOf<CurrencyValue>()
    var throwException = false

    override suspend fun getAllCurrencyValue(base: String): List<CurrencyValue> {
        if(throwException) {
            throw Exception()
        } else {
            return data
        }
    }

    override suspend fun getMultiCurrenciesValue(
        base: String,
        multiCurrency: List<Currency>
    ): List<CurrencyValue> {
        if(throwException) {
            throw Exception()
        } else {
            val multiCurrencyList = mutableListOf<CurrencyValue>()
            multiCurrency.forEach { requiredCurrency ->
                val requiredCurrencyValue =  data.find {
                    requiredCurrency.name == it.name
                }
                requiredCurrencyValue?.let {
                    multiCurrencyList.add(it)
                }
            }
            return multiCurrencyList
        }
    }
}