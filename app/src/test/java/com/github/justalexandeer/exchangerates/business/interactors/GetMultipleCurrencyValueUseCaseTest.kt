package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.fake.FakeTestCurrencyRepository
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.fake.FakeTestCurrencyValueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Test

class GetMultipleCurrencyValueUseCaseTest {
    private val fakeTestCurrencyValueRepository = FakeTestCurrencyValueRepository()
    private val fakeTestCurrencyRepository = FakeTestCurrencyRepository()
    private val getMultipleCurrencyValueUseCase = GetMultipleCurrencyValueUseCase(
        fakeTestCurrencyValueRepository,
        fakeTestCurrencyRepository,
        Dispatchers.Unconfined
    )

    @After
    fun resetFakeRepository() {
        fakeTestCurrencyValueRepository.data = mutableListOf()
        fakeTestCurrencyValueRepository.throwException = false
        fakeTestCurrencyRepository.data = mutableListOf()
        fakeTestCurrencyRepository.throwException = false
    }

    @Test
    fun useCase_localThrowException_returnErrorDataState() {
        fakeTestCurrencyRepository.throwException = true
        val expectedResult = DataState<List<CurrencyValue>>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Local
        )

        runBlocking {
            val dataState = getMultipleCurrencyValueUseCase.invoke(Currency(""))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCase_remoteThrowException_returnErrorDataState() {
        fakeTestCurrencyValueRepository.throwException = true
        fakeTestCurrencyRepository.data = mutableListOf(Currency(""))
        val expectedResult = DataState<List<CurrencyValue>>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Remote
        )

        runBlocking {
            val dataState = getMultipleCurrencyValueUseCase.invoke(Currency(""))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCase_localNoData_returnErrorDataState() {
        val expectedResult = DataState<List<CurrencyValue>>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Business
        )

        runBlocking {
            val dataState = getMultipleCurrencyValueUseCase.invoke(Currency(""))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCase_localSuccessAndRemoteSuccess_returnSuccessDataState() {
        fakeTestCurrencyRepository.data = mutableListOf(Currency("USD"), Currency("EUR"))
        fakeTestCurrencyValueRepository.data = mutableListOf(
            CurrencyValue("USD", 1f),
            CurrencyValue("EUR", 2f),
            CurrencyValue("RUB", 3f),
        )
        val expectedResult = DataState<List<CurrencyValue>> (
            DataStateStatus.Success,
            mutableListOf(CurrencyValue("USD", 1f), CurrencyValue("EUR", 2f)),
            null,
            null
        )
        runBlocking {
            val dataState = getMultipleCurrencyValueUseCase.invoke(Currency(""))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorMessage, IsEqual(expectedResult.errorMessage))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }
}