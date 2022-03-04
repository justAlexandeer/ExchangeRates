package com.github.justalexandeer.exchangerates.ui.base.model

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue

sealed class CurrencyScreenState {
    data class Success(val currencyValueStateList: List<CurrencyValueState>): CurrencyScreenState()
    data class Error(val errorType: ErrorType): CurrencyScreenState()
    object Loading: CurrencyScreenState()
    object Idle: CurrencyScreenState()
}

sealed class ErrorType {
    data class Business(val errorMessage: String): ErrorType()
    data class LocalOrRemote(val errorMessage: String): ErrorType()
}

data class CurrencyValueState(val currencyValue: CurrencyValue, var isFavorite: Boolean)
