package com.github.justalexandeer.exchangerates.framework.datasource.remote.response

data class CurrencyValueResponse(
    val base: String,
    val results: Map<String,String>,
    val updated: String,
    val ms: Int
)
