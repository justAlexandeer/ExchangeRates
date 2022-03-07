package com.github.justalexandeer.exchangerates.di

import com.github.justalexandeer.exchangerates.business.data.local.abstraction.CurrencyRepository
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyFullNameRepository
import com.github.justalexandeer.exchangerates.business.data.remote.abstraction.CurrencyValueRepository
import com.github.justalexandeer.exchangerates.business.interactors.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddCurrencyToFavoritesUseCase(
        currencyRepository: CurrencyRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = AddCurrencyToFavoritesUseCase(currencyRepository, ioDispatcher)

    @Singleton
    @Provides
    fun provideGetAllCurrencyValueUseCase(
        currencyValueRepository: CurrencyValueRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = GetAllCurrencyValueUseCase(currencyValueRepository, ioDispatcher)

    @Singleton
    @Provides
    fun provideGetCurrenciesFullNameUseCase(
        currencyFullNameRepository: CurrencyFullNameRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = GetCurrenciesFullNameUseCase(currencyFullNameRepository, ioDispatcher)

    @Singleton
    @Provides
    fun provideGetMultipleCurrencyValueUseCase(
        currencyValueRepository: CurrencyValueRepository,
        currencyRepository: CurrencyRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = GetMultipleCurrencyValueUseCase(currencyValueRepository, currencyRepository, ioDispatcher)

    @Singleton
    @Provides
    fun provideRemoveCurrencyFromFavoritesUseCase(
        currencyRepository: CurrencyRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = RemoveCurrencyFromFavoritesUseCase(currencyRepository, ioDispatcher)

    @Singleton
    @Provides
    fun provideGetAllFavoritesCurrency(
        currencyRepository: CurrencyRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = GetAllFavoritesCurrency(currencyRepository, ioDispatcher)
}