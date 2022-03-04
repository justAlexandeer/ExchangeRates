package com.github.justalexandeer.exchangerates.business.data.local

import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): DataState<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT) {
                DataState(DataStateStatus.Success, cacheCall.invoke(), null, null)
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    DataState(
                        DataStateStatus.Error,
                        null,
                        CACHE_ERROR_TIMEOUT,
                        DataStateErrorType.Local
                    )
                }
                else -> {
                    DataState(
                        DataStateStatus.Error,
                        null,
                        CACHE_ERROR_UNKNOWN,
                        DataStateErrorType.Local
                    )
                }
            }
        }
    }
}

const val CACHE_TIMEOUT = 2000L

const val CACHE_ERROR_TIMEOUT = "Cache timeout"
const val CACHE_ERROR_UNKNOWN = "Cache unknown problem"