package com.github.justalexandeer.exchangerates.ui.choose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.justalexandeer.exchangerates.R
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.business.domain.model.CurrencyFullName

@Composable
fun ChooseCurrencyScreenSuccess(
    listOfCurrencyFullName: List<CurrencyFullName>,
    onCurrencyClick: (Currency) -> Unit,
    setOpenDialogValue: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(listOfCurrencyFullName) { index, currencyFullNameItem ->
            CurrencyFullNameItem(currencyFullNameItem, onCurrencyClick, setOpenDialogValue)
            if(index < listOfCurrencyFullName.lastIndex) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun CurrencyFullNameItem(
    currencyFullName: CurrencyFullName,
    onCurrencyClick: (Currency) -> Unit,
    setOpenDialogValue: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onCurrencyClick(Currency(currencyFullName.name))
                setOpenDialogValue(false)
            }
            .padding(dimensionResource(R.dimen.grid_3))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currencyFullName.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = currencyFullName.fullName,
            maxLines = 2,
            textAlign = TextAlign.End
        )
    }
}