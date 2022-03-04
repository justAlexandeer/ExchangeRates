package com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.fake

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.domain.model.Currency


class FakeTestCurrencyRepository: CurrencyRepository {
    var data = mutableListOf<Currency>()
    var throwException = false

    override suspend fun getAllFavoritesCurrency(): List<Currency> {
        if(throwException) {
            throw Exception()
        } else {
            return data.toList()
        }
    }

    override suspend fun saveCurrencyToFavorites(currency: Currency) {
        if(throwException) {
            throw Exception()
        } else {
            val duplicatedCurrency = data.find {
                it.name == currency.name
            }
            duplicatedCurrency?.let {
                data.remove(it)
            }
            data.add(currency)
        }
    }

    override suspend fun removeCurrencyFromFavorites(currency: Currency) {
        if(throwException) {
            throw Exception()
        } else {
            data.remove(currency)
        }
    }

}