package com.github.justalexandeer.exchangerates.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


class ExchangeRatesNavigation(navHostController: NavHostController) {
    val navigateToFavoritesScreen: () -> Unit = {
        navHostController.navigate(ExchangeRatesDestinations.FAVORITES_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPopularScreen: () -> Unit = {
        navHostController.navigate(ExchangeRatesDestinations.POPULAR_ROUTE) {
            popUpTo(ExchangeRatesDestinations.POPULAR_ROUTE) {
                saveState = false
            }
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