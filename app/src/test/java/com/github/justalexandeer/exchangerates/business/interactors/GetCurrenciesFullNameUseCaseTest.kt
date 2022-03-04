package com.github.justalexandeer.exchangerates.business.interactors

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.DataState
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateErrorType
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.fake.FakeTestCurrencyFullNameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Test

class GetCurrenciesFullNameUseCaseTest {

    private val fakeTestCurrencyFullNameRepository = FakeTestCurrencyFullNameRepository()
    private val getCurrenciesFullNameUseCase = GetCurrenciesFullNameUseCase(
        fakeTestCurrencyFullNameRepository,
        Dispatchers.Unconfined
    )

    @After
    fun resetRepository() {
        fakeTestCurrencyFullNameRepository.data = mutableListOf()
        fakeTestCurrencyFullNameRepository.throwException = false
    }

    @Test
    fun useCase_remoteThrowException_returnErrorDataState() {
        fakeTestCurrencyFullNameRepository.throwException = true
        val expectedResult = DataState<List<CurrencyFullName>>(
            DataStateStatus.Error,
            null,
            "",
            DataStateErrorType.Remote
        )
        runBlocking {
            val dataState = getCurrenciesFullNameUseCase.invoke()
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

    @Test
    fun useCaseRemoteSuccess_returnSuccessDataState() {
        val listOfCurrencyFullName = mutableListOf(
            CurrencyFullName("USD", " United States dollars"),
            CurrencyFullName("EUR", "Euro"),
            CurrencyFullName("RUB", "Russian Ruble"),
        )
        val expectedResult = DataState<List<CurrencyFullName>> (
            DataStateStatus.Success,
            listOfCurrencyFullName,
            null,
            null
        )
        fakeTestCurrencyFullNameRepository.data = listOfCurrencyFullName
        runBlocking {
            val dataState = getCurrenciesFullNameUseCase.invoke()
            assertThat(dataState.status, IsEqual(expectedResult.status))
            assertThat(dataState.data, IsEqual(expectedResult.data))
            assertThat(dataState.errorMessage, IsEqual(expectedResult.errorMessage))
            assertThat(dataState.errorType, IsEqual(expectedResult.errorType))
        }
    }

}