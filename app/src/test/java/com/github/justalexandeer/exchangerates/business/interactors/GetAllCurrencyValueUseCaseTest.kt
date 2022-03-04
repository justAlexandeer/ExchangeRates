package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.fake.FakeTestCurrencyValueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Test

class GetAllCurrencyValueUseCaseTest {

    private val fakeTestCurrencyValueRepository = FakeTestCurrencyValueRepository()
    private val getAllCurrencyValueUseCase = GetAllCurrencyValueUseCase(
        fakeTestCurrencyValueRepository,
        Dispatchers.Unconfined
    )

    @After
    fun resetFakeRepository() {
        fakeTestCurrencyValueRepository.data = mutableListOf()
        fakeTestCurrencyValueRepository.throwException = false
    }

    @Test
    fun useCase_remoteThrowException_returnErrorDataState() {
        fakeTestCurrencyValueRepository.throwException = true
        val expectedResult = DataState<List<CurrencyValue>>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Remote
        )

        runBlocking {
            val dataState = getAllCurrencyValueUseCase.invoke(Currency("USD"))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCase_remoteSuccess_returnSuccessDataState() {
        val listOfCurrencyValue = mutableListOf(
            CurrencyValue("USD", 1f),
            CurrencyValue("EUR", 2f),
            CurrencyValue("RUB", 3f),
        )
        val expectedResult = DataState<List<CurrencyValue>> (
            DataStateStatus.Success,
            listOfCurrencyValue,
            null,
            null
        )
        fakeTestCurrencyValueRepository.data = listOfCurrencyValue
        runBlocking {
            val dataState = getAllCurrencyValueUseCase.invoke(Currency("USD"))
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorMessage, IsEqual(expectedResult.errorMessage))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

}