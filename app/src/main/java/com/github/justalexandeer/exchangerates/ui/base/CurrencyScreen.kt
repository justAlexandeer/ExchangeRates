package com.github.justalexandeer.exchangerates.ui.base

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController

@SuppressLint("UnrememberedMutableState")
@Composable
fun CurrencyScreen(isFavorite: Boolean, navController: NavHostController) {

}

@Composable
fun ErrorScreen(text: String) {
    Text(text = text)
}