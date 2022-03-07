package com.github.justalexandeer.exchangerates.ui.choose.model

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName

sealed class ChooseCurrencyScreenState {
    object Loading: ChooseCurrencyScreenState()
    object Idle: ChooseCurrencyScreenState()
    data class Error(val errorText: String): ChooseCurrencyScreenState()
    data class Success(val listOfCurrencyFullName: List<CurrencyFullName>): ChooseCurrencyScreenState()
}