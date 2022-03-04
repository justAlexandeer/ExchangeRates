package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyFullNameRepository
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.data.remote.safeApiCall
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrenciesFullNameUseCase @Inject constructor(
    private val currencyFullNameRepository: CurrencyFullNameRepository,
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(): DataState<List<CurrencyFullName>?> = withContext(dispatcherIO) {
        safeApiCall(dispatcherIO) {
            currencyFullNameRepository.getAllCurrencyFullName()
        }
    }
}