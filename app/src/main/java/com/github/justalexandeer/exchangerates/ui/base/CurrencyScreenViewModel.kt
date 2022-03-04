package com.github.justalexandeer.exchangerates.ui.base

import androidx.lifecycle.ViewModel
import com.github.justalexandeer.exchangerates.ui.base.model.CurrencyScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencyScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _currencyScreenState: MutableStateFlow<CurrencyScreenState> =
        MutableStateFlow(CurrencyScreenState.Idle)
    val currencyScreenState: StateFlow<CurrencyScreenState> = _currencyScreenState



}