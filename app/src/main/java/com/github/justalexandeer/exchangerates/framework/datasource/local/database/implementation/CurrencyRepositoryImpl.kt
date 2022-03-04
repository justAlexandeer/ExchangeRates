package com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.dao.CurrencyEntityDao
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.mapper.CurrencyMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val currencyEntityDao: CurrencyEntityDao,
    private val currencyMapper: CurrencyMapper
): CurrencyRepository {
    override suspend fun getAllFavoritesCurrency(): List<Currency> =
        currencyMapper.fromListCurrencyEntityToListCurrency(currencyEntityDao.getAllCurrencyEntity())

    override suspend fun saveCurrencyToFavorites(currency: Currency) =
        currencyEntityDao.saveCurrencyEntity(currencyMapper.fromCurrencyToCurrencyEntity(currency))

    override suspend fun removeCurrencyFromFavorites(currency: Currency) =
        currencyEntityDao.removeCurrencyEntity(currency.name)

}