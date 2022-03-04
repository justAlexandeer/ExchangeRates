package com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.fake

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyFullNameRepository
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName
import org.junit.Assert.*

class FakeTestCurrencyFullNameRepository: CurrencyFullNameRepository {
    var data = mutableListOf<CurrencyFullName>()
    var throwException = false

    override suspend fun getAllCurrencyFullName(): List<CurrencyFullName> {
        if(throwException) {
            throw Exception()
        } else {
            return data
        }
    }

}