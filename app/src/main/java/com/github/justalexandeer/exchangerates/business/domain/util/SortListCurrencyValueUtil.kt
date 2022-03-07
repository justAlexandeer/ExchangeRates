package com.github.justalexandeer.exchangerates.business.domain.util

import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue
import com.github.justalexandeer.exchangerates.business.domain.state.SortType

fun sortListCurrencyValueUtil(
    sortType: SortType,
    unsortedList: List<CurrencyValue>
): List<CurrencyValue> {
    return when(sortType) {
        SortType.AlphabeticalAsc -> unsortedList.sortedBy { it.name }
        SortType.AlphabeticalDesc -> unsortedList.sortedByDescending { it.name }
        SortType.ValueAsc -> unsortedList.sortedBy { it.value }
        SortType.ValueDesc -> unsortedList.sortedByDescending { it.value }
    }
}