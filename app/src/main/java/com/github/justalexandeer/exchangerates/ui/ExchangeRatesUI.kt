package com.github.justalexandeer.exchangerates.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.justalexandeer.exchangerates.R

@SuppressLint("RememberReturnType")
@Composable
fun ExchangeRatesUI() {
    val navController = rememberNavController()
    val exchangeRatesNavigation = remember { ExchangeRatesNavigation(navController) }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNavigation {
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == ExchangeRatesDestinations.POPULAR_ROUTE
                    } == true,
                    icon = { Icon(Icons.Outlined.Paid, contentDescription = null) },
                    label = { Text(text = stringResource(R.string.navigation_item_bottom_popular))},
                    onClick = { exchangeRatesNavigation.navigateToPopularScreen() }
                )
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == ExchangeRatesDestinations.FAVORITES_ROUTE
                    } == true,
                    icon = { Icon(Icons.Outlined.StarBorder, contentDescription = null) },
                    label = { Text(text = stringResource(R.string.navigation_item_bottom_favorite))},
                    onClick = { exchangeRatesNavigation.navigateToFavoritesScreen() }
                )
            }
        }
    ) {
        Box(
            Modifier.padding(it)
        ) {
            ExchangeRatesNavGraph(navController, exchangeRatesNavigation)
        }
    }
}