package com.github.justalexandeer.exchangerates.ui.currency.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.justalexandeer.exchangerates.R
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyValue

@Composable
fun CurrencyListSuccessComponent(
    listOfCurrencyValueState: List<CurrencyValueState>,
    onFavoriteIconClick: (CurrencyValueState) -> Unit
) {
    LazyColumn {
        items(listOfCurrencyValueState) { currencyValueState ->
            CurrencyValueComponent(currencyValueState, onFavoriteIconClick)
        }
    }
}

@Composable
fun CurrencyValueComponent(
    currencyValueState: CurrencyValueState,
    onFavoriteIconClick: (CurrencyValueState) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(vertical = dimensionResource(R.dimen.grid_1)),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.grid_1_5))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.grid_0_5)),
                    fontWeight = FontWeight.Bold,
                    text = currencyValueState.currencyValue.name
                )
                Text(text = currencyValueState.currencyValue.value.toString())
            }
            Icon(
                modifier = Modifier
                    .clickable { onFavoriteIconClick(currencyValueState) }
                    .padding(dimensionResource(R.dimen.grid_1)),
                imageVector = if (currencyValueState.isFavorite)
                    Icons.Outlined.Star else
                    Icons.Outlined.StarOutline,
                contentDescription = null
            )
        }
    }
}

sealed class CurrencyListComponentState {
    object Loading : CurrencyListComponentState()
    data class Success(val listOfCurrencyValueState: List<CurrencyValueState>) :
        CurrencyListComponentState()
    data class Error(val errorType: CurrencyListComponentErrorType) : CurrencyListComponentState()
    object Idle : CurrencyListComponentState()
}

sealed class CurrencyListComponentErrorType {
    data class Business(val errorMessage: String) : CurrencyListComponentErrorType()
    data class LocalOrRemote(val errorMessage: String) : CurrencyListComponentErrorType()
}

data class CurrencyValueState(
    val currencyValue: CurrencyValue, var isFavorite: Boolean
)