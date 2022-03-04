package com.github.justalexandeer.exchangerates.framework.datasource.local.database.mapper

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.entity.CurrencyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyMapper @Inject constructor() {
    fun fromListCurrencyEntityToListCurrency(listOfCurrencyEntity: List<CurrencyEntity>): List<Currency> =
        listOfCurrencyEntity.map {
            Currency(it.name)
        }

    fun fromCurrencyToCurrencyEntity(currency: Currency): CurrencyEntity =
        CurrencyEntity(id = 0L, name = currency.name)
}