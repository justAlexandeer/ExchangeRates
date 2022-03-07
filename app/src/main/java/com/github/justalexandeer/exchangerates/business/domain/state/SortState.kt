package com.github.justalexandeer.exchangerates.business.domain.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class SortType: Parcelable {
    @Parcelize object AlphabeticalAsc : SortType()
    @Parcelize object AlphabeticalDesc : SortType()
    @Parcelize object ValueAsc : SortType()
    @Parcelize object ValueDesc : SortType()
}