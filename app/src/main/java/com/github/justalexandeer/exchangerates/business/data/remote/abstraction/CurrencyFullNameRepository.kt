package com.github.justalexandeer.exchangerates.business.data.remote.abstraction

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName

interface CurrencyFullNameRepository {
    suspend fun getAllCurrencyFullName(): List<CurrencyFullName>
}