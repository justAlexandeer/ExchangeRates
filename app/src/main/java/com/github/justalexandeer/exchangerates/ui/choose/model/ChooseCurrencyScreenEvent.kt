package com.github.justalexandeer.exchangerates.ui.choose.model

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName

sealed class ChooseCurrencyScreenEvent {
    object LoadListCurrencyFullName: ChooseCurrencyScreenEvent()
}