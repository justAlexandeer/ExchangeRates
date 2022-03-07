package com.github.justalexandeer.exchangerates.ui.currency

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenEvent
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenState
import com.github.justalexandeer.exchangerates.R
import com.github.justalexandeer.exchangerates.business.domain.state.SortType
import com.github.justalexandeer.exchangerates.ui.ExchangeRatesNavigation
import com.github.justalexandeer.exchangerates.ui.choose.ChooseCurrencyScreen
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListLoadingComponent
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListSuccessComponent
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListComponentState
import com.github.justalexandeer.exchangerates.ui.currency.component.CurrencyListErrorComponent

@Composable
fun CurrencyScreen(
    isFavorite: Boolean,
    navController: NavHostController,
    exchangeRatesNavigation: ExchangeRatesNavigation,
    viewModel: CurrencyScreenViewModel = hiltViewModel()
) {
    val currencyScreenState = viewModel.currencyScreenState.collectAsState()
    val (openDialogValue, setOpenDialogValue) = rememberSaveable { mutableStateOf(false) }
    val sortType =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<SortType>("SortType")
            ?.observeAsState()

    sortType?.value?.let {
        if (currencyScreenState.value.sortType != it) {
            viewModel.obtainEvent(CurrencyScreenEvent.SortTypeChange(isFavorite, it))
        }
    }

    var loadDataLaunchedEffect by rememberSaveable { mutableStateOf(false) }
    if (!loadDataLaunchedEffect) {
        LaunchedEffect(Unit) {
            viewModel.obtainEvent(CurrencyScreenEvent.LoadListCurrencyValue(isFavorite))
            loadDataLaunchedEffect = true
        }
    }

    CurrencyScreenContent(
        isFavorite,
        exchangeRatesNavigation,
        currencyScreenState.value,
        { viewModel.obtainEvent(it) },
        openDialogValue,
        setOpenDialogValue
    )
}

@Composable
fun CurrencyScreenContent(
    isFavorite: Boolean,
    exchangeRatesNavigation: ExchangeRatesNavigation,
    currencyScreenState: CurrencyScreenState,
    sendEvent: (CurrencyScreenEvent) -> Unit,
    openDialogValue: Boolean,
    setOpenDialogValue: (Boolean) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.grid_1_5))
            .padding(top = dimensionResource(R.dimen.grid_1_5))
    ) {
        Row {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensionResource(R.dimen.grid_1)),
                onClick = {
                    setOpenDialogValue(true)
                }
            ) {
                Text(
                    text = stringResource(R.string.choose_currency_screen_title),
                    maxLines = 2
                )
            }
            Button(onClick = {
                exchangeRatesNavigation.navigateToSortingScreen()
            }) {
                Text(stringResource(R.string.currency_screen_button_sort))
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.grid_1_5)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.currency_screen_base_currency) +
                        currencyScreenState.baseCurrency.name,
                maxLines = 2
            )
            Text(
                text = stringResource(R.string.currency_screen_sort_by) +
                        currencyScreenState.sortType.javaClass.simpleName,
                maxLines = 2
            )
        }
        Column(
            Modifier
                .weight(1f)
        ) {
            when (val currentState = currencyScreenState.currencyListComponentState) {
                is CurrencyListComponentState.Success -> {
                    CurrencyListSuccessComponent(
                        currentState.listOfCurrencyValueState,
                        {
                            sendEvent(
                                CurrencyScreenEvent.OnCurrencyFavoriteButtonClick(
                                    it,
                                    isFavorite
                                )
                            )
                        }
                    )
                }
                is CurrencyListComponentState.Error -> {
                    CurrencyListErrorComponent(
                        currentState.errorType,
                        { sendEvent(CurrencyScreenEvent.LoadListCurrencyValue(isFavorite)) }
                    )
                }
                CurrencyListComponentState.Idle -> {}
                CurrencyListComponentState.Loading -> {
                    CurrencyListLoadingComponent()
                }
            }
        }
    }

    if (openDialogValue) {
        ChooseCurrencyScreen(
            { sendEvent(CurrencyScreenEvent.BaseCurrencyChange(it, isFavorite)) },
            { setOpenDialogValue(it) }
        )
    }
}
