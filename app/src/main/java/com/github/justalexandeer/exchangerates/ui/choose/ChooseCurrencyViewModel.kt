package com.github.justalexandeer.exchangerates.ui.choose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.justalexandeer.exchangerates.business.domain.state.DataStateStatus
import com.github.justalexandeer.exchangerates.business.interactors.GetCurrenciesFullNameUseCase
import com.github.justalexandeer.exchangerates.ui.choose.model.ChooseCurrencyScreenEvent
import com.github.justalexandeer.exchangerates.ui.choose.model.ChooseCurrencyScreenState
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListComponentState
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCurrencyViewModel @Inject constructor(
    private val getAllCurrencyFullNameUseCase: GetCurrenciesFullNameUseCase
): ViewModel() {

    private val _chooseCurrencyScreenState: MutableStateFlow<ChooseCurrencyScreenState> =
        MutableStateFlow(ChooseCurrencyScreenState.Idle)
    val chooseCurrencyScreenState: StateFlow<ChooseCurrencyScreenState> = _chooseCurrencyScreenState

    fun obtainEvent(chooseCurrencyScreenEvent: ChooseCurrencyScreenEvent) {
        when(chooseCurrencyScreenEvent) {
            ChooseCurrencyScreenEvent.LoadListCurrencyFullName -> {
                loadListCurrencyFullName()
            }
        }
    }

    private fun loadListCurrencyFullName() {
        _chooseCurrencyScreenState.value = ChooseCurrencyScreenState.Loading
        viewModelScope.launch {
            val dataState = getAllCurrencyFullNameUseCase.invoke()
            if(dataState.status == DataStateStatus.Success) {
                dataState.data?.let {
                    _chooseCurrencyScreenState.value = ChooseCurrencyScreenState.Success(dataState.data)
                }
            } else {
                dataState.errorMessage?.let {
                    _chooseCurrencyScreenState.value = ChooseCurrencyScreenState.Error(dataState.errorMessage)
                }
            }
        }
    }

}