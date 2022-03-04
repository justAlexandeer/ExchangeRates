package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.fake.FakeTestCurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Test

class RemoveCurrencyFromFavoritesUseCaseTest {

    private val fakeTestCurrencyRepository = FakeTestCurrencyRepository()
    private val removeCurrencyFromFavoritesUseCase = RemoveCurrencyFromFavoritesUseCase(
        fakeTestCurrencyRepository,
        Dispatchers.Unconfined
    )

    @After
    fun resetFakeTestRepository() {
        fakeTestCurrencyRepository.data = mutableListOf()
        fakeTestCurrencyRepository.throwException = false
    }

    @Test
    fun useCase_localThrowException_returnErrorDataState() {
        fakeTestCurrencyRepository.throwException = true
        val currency = Currency("USD")
        val expectedResult = DataState<Any?>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Local
        )

        runBlocking {
            val dataState = removeCurrencyFromFavoritesUseCase.invoke(currency)
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCase_localSuccess_returnSuccessDataState() {
        fakeTestCurrencyRepository.data =
            mutableListOf(Currency("USD"), Currency("EUR"), Currency("RUB"))
        val expectedDataInLocal = mutableListOf(Currency("USD"), Currency("RUB"))
        val removedCurrency = Currency("EUR")
        val expectedResult = DataState<Any?>(
            DataStateStatus.Success,
            Unit,
            null,
            null
        )
        runBlocking {
            val dataState = removeCurrencyFromFavoritesUseCase.invoke(removedCurrency)
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
            assertThat(fakeTestCurrencyRepository.data.toList(), IsEqual(expectedDataInLocal))
        }
    }

}