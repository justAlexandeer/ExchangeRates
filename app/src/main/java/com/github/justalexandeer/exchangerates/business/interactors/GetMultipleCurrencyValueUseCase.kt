package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.data.local.safeCacheCall
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.data.remote.safeApiCall
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMultipleCurrencyValueUseCase @Inject constructor(
    private val currencyValueRepository: CurrencyValueRepository,
    private val currencyRepository: CurrencyRepository,
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(base: String): DataState<List<CurrencyValue>?> =
        withContext(dispatcherIO) {
            val localDataState = safeCacheCall(dispatcherIO) {
                currencyRepository.getAllFavoritesCurrency()
            }
            if(localDataState.status == DataStateStatus.Error) {
                return@withContext DataState<List<CurrencyValue>?> (
                    localDataState.status,
                    null,
                    localDataState.errorMessage,
                    localDataState.errorType
                )
            } else {
                if(localDataState.data!!.isEmpty()) {
                    return@withContext DataState<List<CurrencyValue>?> (
                        DataStateStatus.Error,
                        null,
                        "No currencies in favorites",
                        DataStateErrorType.Business
                    )
                } else {
                    val remoteDataState: DataState<List<CurrencyValue>?> = safeApiCall(dispatcherIO) {
                        currencyValueRepository.getMultiCurrenciesValue(base, localDataState.data)
                    }
                    return@withContext remoteDataState
                }
            }
        }
}