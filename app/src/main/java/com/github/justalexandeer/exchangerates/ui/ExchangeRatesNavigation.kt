package com.github.justalexandeer.exchangerates.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


class ExchangeRatesNavigation(navHostController: NavHostController) {
    val navigateToPopularScreen: () -> Unit = {
        navHostController.navigate(ExchangeRatesDestinations.POPULAR_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToFavoritesScreen: () -> Unit = {
        navHostController.navigate(ExchangeRatesDestinations.FAVORITES_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSortingScreen: () -> Unit = {
        navHostController.navigate(ExchangeRatesDestinations.SORING_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
}