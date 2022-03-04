package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.data.local.safeCacheCall
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddCurrencyToFavoritesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(currency: Currency): DataState<Any?> = withContext(dispatcherIO) {
        safeCacheCall(dispatcherIO) {
            currencyRepository.saveCurrencyToFavorites(currency)
        }
    }
}