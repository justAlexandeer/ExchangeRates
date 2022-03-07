package com.github.justalexandeer.exchangerates

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.CurrencyRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.ApiCurrency
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyFullNameRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyValueRepositoryImpl
import com.github.justalexandeer.exchangerates.ui.ExchangeRatesUI
import com.github.justalexandeer.exchangerates.ui.theme.ExchangeRatesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ExchangeRates)
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRatesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExchangeRatesUI()
                }
            }
        }
    }
}
