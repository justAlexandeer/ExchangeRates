package com.github.justalexandeer.exchangerates.di

import com.github.justalexandeer.exchangerates.framework.datasource.remote.ApiCurrency
import com.github.justalexandeer.exchangerates.framework.datasource.remote.ServiceCurrency
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiCurrency(serviceCurrency: ServiceCurrency): ApiCurrency {
        return serviceCurrency.retrofit.create(ApiCurrency::class.java)
    }
}