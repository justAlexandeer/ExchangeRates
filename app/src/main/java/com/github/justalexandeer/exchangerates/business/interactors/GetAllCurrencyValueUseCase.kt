package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.data.remote.safeApiCall
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCurrencyValueUseCase @Inject constructor(
    private val currencyValueRepository: CurrencyValueRepository,
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(baseCurrency: Currency): DataState<List<CurrencyValue>?> =
        withContext(dispatcherIO) {
            safeApiCall(dispatcherIO) {
                currencyValueRepository.getAllCurrencyValue(baseCurrency.name)
            }
        }
}