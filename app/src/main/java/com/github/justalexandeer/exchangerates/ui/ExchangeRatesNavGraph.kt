package com.github.justalexandeer.exchangerates.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.justalexandeer.exchangerates.ui.currency.CurrencyScreen
import com.github.justalexandeer.exchangerates.ui.currency.CurrencyScreenViewModel
import com.github.justalexandeer.exchangerates.ui.sorting.SortingScreen

@Composable
fun ExchangeRatesNavGraph(
    navController: NavHostController,
    exchangeRatesNavigation: ExchangeRatesNavigation
) {
    NavHost(navController = navController, startDestination = ExchangeRatesDestinations.POPULAR_ROUTE) {
        composable(ExchangeRatesDestinations.POPULAR_ROUTE) {
            CurrencyScreen(isFavorite = false, navController = navController, exchangeRatesNavigation)
        }
        composable(ExchangeRatesDestinations.FAVORITES_ROUTE) {
            CurrencyScreen(isFavorite = true, navController = navController, exchangeRatesNavigation)
        }
        composable(ExchangeRatesDestinations.SORING_ROUTE) {
            SortingScreen(navController)
        }
    }
}