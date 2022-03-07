package com.github.justalexandeer.exchangerates.ui.currency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.*
import com.github.justalexandeer.exchangerates.business.domain.util.sortListCurrencyValueUtil
import com.github.justalexandeer.exchangerates.business.interactors.*
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListComponentState
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyValueState
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListComponentErrorType
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenEvent
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyScreenViewModel @Inject constructor(
    private val addCurrencyToFavoritesUseCase: AddCurrencyToFavoritesUseCase,
    private val getAllCurrencyValueUseCase: GetAllCurrencyValueUseCase,
    private val getMultipleCurrencyValueUseCase: GetMultipleCurrencyValueUseCase,
    private val getAllFavoritesCurrency: GetAllFavoritesCurrency,
    private val removeCurrencyFromFavoritesUseCase: RemoveCurrencyFromFavoritesUseCase
) : ViewModel() {
    private var baseCurrency = Currency("USD")
    private var sortType = SortType.AlphabeticalAsc
    private val _currencyScreenState: MutableStateFlow<CurrencyScreenState> =
        MutableStateFlow(
            CurrencyScreenState(
                CurrencyListComponentState.Idle,
                baseCurrency,
                sortType
            )
        )
    val currencyScreenState: StateFlow<CurrencyScreenState> = _currencyScreenState

    fun obtainEvent(event: CurrencyScreenEvent) {
        when (event) {
            is CurrencyScreenEvent.LoadListCurrencyValue -> {
                loadListCurrencyValue(event.isFavoriteScreen)
            }
            is CurrencyScreenEvent.OnCurrencyFavoriteButtonClick -> {
                handleCurrencyFavoriteButtonClick(event.currencyValueState, event.isFavoriteScreen)
            }
            is CurrencyScreenEvent.BaseCurrencyChange -> {
                _currencyScreenState.value =
                    _currencyScreenState.value.copy(baseCurrency = event.currency)
                loadListCurrencyValue(event.isFavoriteScreen)
            }
            is CurrencyScreenEvent.SortTypeChange -> {
                _currencyScreenState.value =
                    _currencyScreenState.value.copy(sortType = event.sortType)
                loadListCurrencyValue(event.isFavoriteScreen)
            }
        }
    }

    private fun handleCurrencyFavoriteButtonClick(
        currentValueState: CurrencyValueState,
        isFavoriteScreen: Boolean
    ) {
        if (isFavoriteScreen) {
            handleFavoriteClickOnFavoriteScreen(currentValueState)
        } else {
            handleFavoriteClickOnPopularScreen(currentValueState)
        }
    }

    private fun handleFavoriteClickOnPopularScreen(currentValueState: CurrencyValueState) {
        if (currentValueState.isFavorite) {
            viewModelScope.launch {
                val dataState =
                    removeCurrencyFromFavoritesUseCase.invoke(Currency(currentValueState.currencyValue.name))
                if (dataState.status == DataStateStatus.Success) {
                    (_currencyScreenState.value.currencyListComponentState
                            as? CurrencyListComponentState.Success)?.let { screenState ->
                        _currencyScreenState.value = _currencyScreenState.value.copy(
                            currencyListComponentState = CurrencyListComponentState.Success(
                                listOfCurrencyValueState = screenState.listOfCurrencyValueState.map {
                                    if (it.currencyValue.name == currentValueState.currencyValue.name) {
                                        return@map it.copy(isFavorite = false)
                                    } else {
                                        return@map it
                                    }
                                }
                            )
                        )
                    }
                } else {
                    // Toast
                }
            }
        } else {
            viewModelScope.launch {
                val dataState =
                    addCurrencyToFavoritesUseCase.invoke(Currency(currentValueState.currencyValue.name))
                if (dataState.status == DataStateStatus.Success) {
                    (_currencyScreenState.value.currencyListComponentState
                            as? CurrencyListComponentState.Success)?.let { screenState ->
                        _currencyScreenState.value = _currencyScreenState.value.copy(
                            currencyListComponentState = CurrencyListComponentState.Success(
                                listOfCurrencyValueState = screenState.listOfCurrencyValueState.map {
                                    if (it.currencyValue.name == currentValueState.currencyValue.name) {
                                        return@map it.copy(isFavorite = true)
                                    } else {
                                        return@map it
                                    }
                                }
                            )
                        )
                    }
                } else {
                    // Toast
                }
            }
        }
    }


    private fun handleFavoriteClickOnFavoriteScreen(currentValueState: CurrencyValueState) {
        if (currentValueState.isFavorite) {
            viewModelScope.launch {
                val dataState =
                    removeCurrencyFromFavoritesUseCase.invoke(Currency(currentValueState.currencyValue.name))
                if (dataState.status == DataStateStatus.Success) {
                    (_currencyScreenState.value.currencyListComponentState
                            as? CurrencyListComponentState.Success)?.let { screenState ->
                        val updatedCurrencyValueStateList =
                            screenState.listOfCurrencyValueState.toMutableList()
                        val removableElement = updatedCurrencyValueStateList.find {
                            it.currencyValue.name == currentValueState.currencyValue.name
                        }
                        removableElement?.let {
                            updatedCurrencyValueStateList.remove(removableElement)
                        }
                        _currencyScreenState.value = _currencyScreenState.value.copy(
                            currencyListComponentState = CurrencyListComponentState.Success(
                                updatedCurrencyValueStateList
                            )
                        )
                    }
                } else {
                    // Toast
                }
            }
        }
    }

    private fun loadListCurrencyValue(isFavoriteScreen: Boolean) {
        if (isFavoriteScreen) {
            loadMultipleCurrencyValue()
        } else {
            loadAllCurrencyValue()
        }
    }

    private fun loadAllCurrencyValue() {
        viewModelScope.launch {
            _currencyScreenState.value = _currencyScreenState.value.copy(
                currencyListComponentState = CurrencyListComponentState.Loading
            )
            val allCurrencyValueDeferred =
                async { getAllCurrencyValueUseCase.invoke(_currencyScreenState.value.baseCurrency) }
            val favoriteCurrencyDeferred =
                async { getAllFavoritesCurrency.invoke() }
            val dataStateAllCurrencyValue = allCurrencyValueDeferred.await()
            val dataStateFavoriteCurrency = favoriteCurrencyDeferred.await()
            if (dataStateAllCurrencyValue.status == DataStateStatus.Success) {
                val sortedAllCurrencyValue = sortListCurrencyValueUtil(
                    _currencyScreenState.value.sortType,
                    dataStateAllCurrencyValue.data!!
                )
                _currencyScreenState.value = _currencyScreenState.value.copy(
                    currencyListComponentState = CurrencyListComponentState.Success(
                        sortedAllCurrencyValue.map { currencyValue ->
                            if (dataStateFavoriteCurrency.status == DataStateStatus.Success) {
                                val isFavorite =
                                    dataStateFavoriteCurrency.data!!.any { favoriteCurrency ->
                                        favoriteCurrency.name == currencyValue.name
                                    }
                                return@map CurrencyValueState(currencyValue, isFavorite)
                            } else {
                                return@map CurrencyValueState(currencyValue, false)
                            }
                        }
                    )
                )
            } else {
                handleErrorDataState(dataStateAllCurrencyValue)
            }
        }
    }

    private fun loadMultipleCurrencyValue() {
        viewModelScope.launch {
            _currencyScreenState.value = _currencyScreenState.value.copy(
                currencyListComponentState = CurrencyListComponentState.Loading
            )
            val dataStateMultipleCurrencyValue =
                getMultipleCurrencyValueUseCase.invoke(_currencyScreenState.value.baseCurrency)
            if (dataStateMultipleCurrencyValue.status == DataStateStatus.Success) {
                val sortedAllCurrencyValue = sortListCurrencyValueUtil(
                    _currencyScreenState.value.sortType,
                    dataStateMultipleCurrencyValue.data!!
                )
                _currencyScreenState.value = _currencyScreenState.value.copy(
                    currencyListComponentState = CurrencyListComponentState.Success(
                        sortedAllCurrencyValue.map {
                            CurrencyValueState(it, true)
                        }
                    )
                )
            } else {
                handleErrorDataState(dataStateMultipleCurrencyValue)
            }
        }
    }

    private fun handleErrorDataState(dataState: DataState<List<CurrencyValue>?>) {
        when (dataState.errorType) {
            DataStateErrorType.Business -> {
                _currencyScreenState.value = _currencyScreenState.value.copy(
                    currencyListComponentState = CurrencyListComponentState.Error(
                        CurrencyListComponentErrorType.Business(
                            dataState.errorMessage!!
                        )
                    )
                )
            }
            DataStateErrorType.Local -> {
                _currencyScreenState.value = _currencyScreenState.value.copy(
                    currencyListComponentState = CurrencyListComponentState.Error(
                        CurrencyListComponentErrorType.LocalOrRemote(
                            dataState.errorMessage!!
                        )
                    )
                )
            }
            DataStateErrorType.Remote -> {
                _currencyScreenState.value = _currencyScreenState.value.copy(
                    currencyListComponentState = CurrencyListComponentState.Error(
                        CurrencyListComponentErrorType.LocalOrRemote(
                            dataState.errorMessage!!
                        )
                    )
                )
            }
        }
    }
}