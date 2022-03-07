package com.github.justalexandeer.exchangerates.ui.currency.model

import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.state.SortType
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListComponentState

data class CurrencyScreenState(
    val currencyListComponentState: CurrencyListComponentState,
    val baseCurrency: Currency,
    val sortType: SortType
)

