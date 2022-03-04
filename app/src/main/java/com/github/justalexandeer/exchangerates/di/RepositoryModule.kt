package com.github.justalexandeer.exchangerates.di

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyFullNameRepository
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.implementation.CurrencyRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyFullNameRepositoryImpl
import com.github.justalexandeer.exchangerates.framework.datasource.remote.implementation.CurrencyValueRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyFullNameRepository(
        currencyFullNameRepositoryImpl: CurrencyFullNameRepositoryImpl
    ): CurrencyFullNameRepository

    @Binds
    abstract fun bindCurrencyValueNameRepository(
        currencyValueRepositoryImpl: CurrencyValueRepositoryImpl
    ): CurrencyValueRepository

    @Binds
    abstract fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}