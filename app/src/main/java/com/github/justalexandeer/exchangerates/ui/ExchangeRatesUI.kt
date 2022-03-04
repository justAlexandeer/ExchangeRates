package com.github.justalexandeer.exchangerates.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("RememberReturnType")
@Composable
fun ExchangeRatesUI() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val exchangeRatesNavigation = remember { ExchangeRatesNavigation(navController) }
            BottomNavigation {
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == ExchangeRatesDestinations.POPULAR_ROUTE
                    } == true,
                    icon = { Icon(Icons.Filled.AccessAlarm, contentDescription = null) },
                    label = { Text(text = "popular")},
                    onClick = { exchangeRatesNavigation.navigateToPopularScreen() }
                )
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == ExchangeRatesDestinations.FAVORITES_ROUTE
                    } == true,
                    icon = { Icon(Icons.Filled.AccessAlarm, contentDescription = null) },
                    label = { Text(text = "favorite")},
                    onClick = { exchangeRatesNavigation.navigateToFavoritesScreen() }
                )
            }
        }
    ) {
        Box {
            ExchangeRatesNavGraph(navController)
        }
    }
}