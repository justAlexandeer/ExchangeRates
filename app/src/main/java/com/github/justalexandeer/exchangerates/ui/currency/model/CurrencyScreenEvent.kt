package com.github.justalexandeer.exchangerates.ui.currency.model

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.SortType
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyValueState

sealed class CurrencyScreenEvent {
    data class LoadListCurrencyValue(val isFavoriteScreen: Boolean) : CurrencyScreenEvent()
    data class BaseCurrencyChange(val currency: Currency, val isFavoriteScreen: Boolean) : CurrencyScreenEvent()
    data class SortTypeChange(val isFavoriteScreen: Boolean, val sortType: SortType) : CurrencyScreenEvent()
    data class OnCurrencyFavoriteButtonClick(
        val currencyValueState: CurrencyValueState,
        val isFavoriteScreen: Boolean
    ) : CurrencyScreenEvent()
}