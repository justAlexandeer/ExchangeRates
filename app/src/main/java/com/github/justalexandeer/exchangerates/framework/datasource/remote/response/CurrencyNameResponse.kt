package com.github.justalexandeer.exchangerates.framework.datasource.remote.response

data class CurrencyNameResponse (
    val currencies: Map<String, String>,
    val ms: Int
)