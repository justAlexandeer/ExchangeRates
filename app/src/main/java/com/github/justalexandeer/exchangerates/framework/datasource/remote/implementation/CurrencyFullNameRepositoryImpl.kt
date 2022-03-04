package com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyFullNameRepository
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName
import com.github.justalexandeer.exchangerates.framework.datasource.remote.ApiCurrency
import com.github.justalexandeer.exchangerates.framework.datasource.remote.mapper.CurrencyFullNameMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFullNameRepositoryImpl @Inject constructor(
    private val apiCurrency: ApiCurrency,
    private val currencyFullNameMapper: CurrencyFullNameMapper
) : CurrencyFullNameRepository {

    override suspend fun getAllCurrencyFullName(): List<CurrencyFullName> =
        currencyFullNameMapper.fromCurrencyNameResponseToListCurrencyFullName(apiCurrency.getAllCurrencies())

}