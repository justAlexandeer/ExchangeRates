package com.github.justalexandeer.exchangerates.ui.choose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.justalexandeer.exchangerates.R
import com.github.justalexandeer.exchangerates.business.domain.model.Currency
import com.github.justalexandeer.exchangerates.ui.choose.component.ChooseCurrencyScreenError
import com.github.justalexandeer.exchangerates.ui.choose.component.ChooseCurrencyScreenLoading
import com.github.justalexandeer.exchangerates.ui.choose.component.ChooseCurrencyScreenSuccess
import com.github.justalexandeer.exchangerates.ui.choose.model.ChooseCurrencyScreenEvent
import com.github.justalexandeer.exchangerates.ui.choose.model.ChooseCurrencyScreenState

@Composable
fun ChooseCurrencyScreen(
    onCurrencyClick: (Currency) -> Unit,
    setOpenDialogValue: (Boolean) -> Unit,
    viewModel: ChooseCurrencyViewModel = hiltViewModel()
) {
    val chooseCurrencyScreenState = viewModel.chooseCurrencyScreenState.collectAsState()
    val configuration = LocalConfiguration.current
    Dialog(
        onDismissRequest = {
            setOpenDialogValue(false)
        }
    ) {
        Surface(
            modifier = Modifier
                .height((configuration.screenHeightDp / 100f * 70).toInt().dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                Modifier.fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.grid_1_5)),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.choose_currency_screen_title)
                )
                Column(Modifier.weight(1f)) {
                    when (val currentState = chooseCurrencyScreenState.value) {
                        is ChooseCurrencyScreenState.Error -> {
                            ChooseCurrencyScreenError(
                                currentState.errorText,
                                { viewModel.obtainEvent(ChooseCurrencyScreenEvent.LoadListCurrencyFullName) }
                            )
                        }
                        is ChooseCurrencyScreenState.Success -> {
                            ChooseCurrencyScreenSuccess(
                                currentState.listOfCurrencyFullName,
                                onCurrencyClick,
                                setOpenDialogValue
                            )
                        }
                        ChooseCurrencyScreenState.Loading -> {
                            ChooseCurrencyScreenLoading()
                        }
                        ChooseCurrencyScreenState.Idle -> {}
                    }
                }
                Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)
                Text(
                    modifier = Modifier
                        .clickable { setOpenDialogValue(false) }
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.grid_1_5)),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.dismiss_button)
                )
            }
        }
    }
    var launchedEffect by rememberSaveable { mutableStateOf(false) }
    if (!launchedEffect) {
        LaunchedEffect(Unit) {
            viewModel.obtainEvent(ChooseCurrencyScreenEvent.LoadListCurrencyFullName)
            launchedEffect = true
        }
    }
}
