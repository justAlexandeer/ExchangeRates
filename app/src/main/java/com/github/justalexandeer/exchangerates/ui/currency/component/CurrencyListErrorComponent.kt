package com.github.justalexandeer.exchangerates.ui.currency.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.github.justalexandeer.exchangerates.R

@Composable
fun CurrencyListErrorComponent(
    errorType: CurrencyListComponentErrorType,
    retryLoadData: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(R.dimen.grid_10)),
            imageVector = if (errorType is CurrencyListComponentErrorType.LocalOrRemote)
                Icons.Outlined.SentimentVeryDissatisfied else
                Icons.Outlined.Favorite,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.grid_2)),
            text = when(errorType) {
                is CurrencyListComponentErrorType.Business -> errorType.errorMessage
                is CurrencyListComponentErrorType.LocalOrRemote -> errorType.errorMessage
            }
        )
        if (errorType is CurrencyListComponentErrorType.LocalOrRemote) {
            Button(onClick = { retryLoadData() }) {
                Text(text = stringResource(R.string.retry_button))
            }
        }
    }
}