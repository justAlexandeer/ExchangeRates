package com.github.justalexandeer.exchangerates

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.CurrencyRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.ApiCurrency
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyFullNameRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyValueRepositoryImpl
import com.github.justalexandeer.exchangerates.ui.theme.ExchangeRatesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currencyValueRepositoryImpl: CurrencyValueRepositoryImpl

    @Inject
    lateinit var currencyFullNameRepositoryImpl: CurrencyFullNameRepositoryImpl

    @Inject
    lateinit var currencyRepositoryImpl: CurrencyRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRatesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(
                        "Android",
                        currencyValueRepositoryImpl,
                        currencyFullNameRepositoryImpl,
                        currencyRepositoryImpl
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    currencyValueRepositoryImpl: CurrencyValueRepositoryImpl,
    currencyFullNameRepositoryImpl: CurrencyFullNameRepositoryImpl,
    currencyRepositoryImpl: CurrencyRepositoryImpl
) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            withContext(Dispatchers.IO) {
                val listOfCurrency = mutableListOf(
                    Currency("USD"),
                    Currency("EUR"),
                    Currency("ETB"),
                    Currency("GHS")
                )
                currencyRepositoryImpl.saveCurrencyToFavorites(listOfCurrency[1])
                currencyRepositoryImpl.saveCurrencyToFavorites(listOfCurrency[2])
                currencyRepositoryImpl.saveCurrencyToFavorites(listOfCurrency[3])
                var test1 = currencyRepositoryImpl.getAllFavoritesCurrency()
                Log.i("TAG", "Greeting: $test1")
                currencyRepositoryImpl.removeCurrencyFromFavorites(listOfCurrency[2])
                test1 = currencyRepositoryImpl.getAllFavoritesCurrency()
                Log.i("TAG", "Greeting: $test1")
            }
        }
    }) {
        Text(text = "Hello $name!")
    }
}