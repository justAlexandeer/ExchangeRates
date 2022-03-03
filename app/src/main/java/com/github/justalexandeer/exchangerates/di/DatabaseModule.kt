package com.github.justalexandeer.exchangerates.di

import android.content.Context
import androidx.room.Room
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.AppDatabase
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.DATABASE_NAME
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.mapper.CurrencyEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideCurrencyEntityDao(database: AppDatabase): CurrencyEntityDao {
        return database.currencyDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}