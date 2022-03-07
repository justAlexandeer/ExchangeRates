package com.github.justalexandeer.exchangerates.ui.choose.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.github.justalexandeer.exchangerates.R

@Composable
fun ChooseCurrencyScreenError(
    errorMessage: String,
    retryLoadData: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(modifier = Modifier.padding(dimensionResource(R.dimen.grid_2)), text = errorMessage)
        Button(onClick = { retryLoadData() }) {
            Text(text = stringResource(R.string.retry_button))
        }
    }
}