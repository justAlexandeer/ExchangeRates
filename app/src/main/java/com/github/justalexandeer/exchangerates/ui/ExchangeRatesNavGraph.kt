package com.github.justalexandeer.exchangerates.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.justalexandeer.exchangerates.ui.base.CurrencyScreen
import com.github.justalexandeer.exchangerates.ui.sorting.SortingScreen

@Composable
fun ExchangeRatesNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = ExchangeRatesDestinations.POPULAR_ROUTE) {
        composable(ExchangeRatesDestinations.POPULAR_ROUTE) {
            CurrencyScreen(isFavorite = false, navController = navController)
        }
        composable(ExchangeRatesDestinations.FAVORITES_ROUTE) {
            CurrencyScreen(isFavorite = true, navController = navController)
        }
        composable(ExchangeRatesDestinations.SORING_ROUTE) {
            SortingScreen(navController)
        }
    }
}