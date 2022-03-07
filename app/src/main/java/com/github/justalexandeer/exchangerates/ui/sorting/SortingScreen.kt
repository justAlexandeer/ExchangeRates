package com.github.justalexandeer.exchangerates.ui.sorting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.justalexandeer.exchangerates.R
import com.github.justalexandeer.exchangerates.business.domain.state.SortType
import com.github.justalexandeer.exchangerates.ui.currency.CurrencyScreenViewModel
import com.github.justalexandeer.exchangerates.ui.currency.model.CurrencyScreenEvent

@Composable
fun SortingScreen(
    navHostController: NavHostController,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.grid_1_5))
            .padding(top = dimensionResource(R.dimen.grid_1))
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.sort_screen_sort_title),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )
        Text(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.grid_2)),
            text = stringResource(R.string.sort_screen_sort_alphabetical_title),
        )
        Text(
            modifier = Modifier
                .clickable {
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        "SortType",
                        SortType.AlphabeticalAsc
                    )
                    navHostController.popBackStack()
                }
                .padding(
                    vertical = dimensionResource(R.dimen.grid_1),
                    horizontal = dimensionResource(R.dimen.grid_1)
                ),
            text = stringResource(R.string.sort_screen_sort_alphabetical_type_asc),
        )
        Text(
            modifier = Modifier
                .clickable {
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        "SortType",
                        SortType.AlphabeticalDesc
                    )
                    navHostController.popBackStack()
                }
                .padding(
                    vertical = dimensionResource(R.dimen.grid_1),
                    horizontal = dimensionResource(R.dimen.grid_1)
                ),
            text = stringResource(R.string.sort_screen_sort_alphabetical_type_desc),
        )
        Text(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.grid_2)),
            text = stringResource(R.string.sort_screen_sort_value_title),
        )
        Text(
            modifier = Modifier
                .clickable {
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        "SortType",
                        SortType.ValueAsc
                    )
                    navHostController.popBackStack()
                }
                .padding(
                    vertical = dimensionResource(R.dimen.grid_1),
                    horizontal = dimensionResource(R.dimen.grid_1)
                ),
            text = stringResource(R.string.sort_screen_sort_value_type_asc),
        )
        Text(
            modifier = Modifier
                .clickable {
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        "SortType",
                        SortType.ValueDesc
                    )
                    navHostController.popBackStack()
                }
                .padding(
                    vertical = dimensionResource(R.dimen.grid_1),
                    horizontal = dimensionResource(R.dimen.grid_1)
                ),
            text = stringResource(R.string.sort_screen_sort_value_type_desc),
        )
    }
}