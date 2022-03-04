package com.github.justalexandeer.exchangerates.ui.base.model

sealed class CurrencyScreenEvent {
    data class Test1(val text: String): CurrencyScreenEvent()
    data class Test2(val text: String): CurrencyScreenEvent()
}