package com.github.justalexandeer.exchangerates.business.data.local.abstraction

import com.github.justalexandeer.exchangerates.business.domain.model.Currency

interface CurrencyRepository {
    suspend fun getAllFavoritesCurrency(): List<Currency>
    suspend fun saveCurrencyToFavorites(currency: Currency)
    suspend fun removeCurrencyFromFavorites(currency: Currency)
}