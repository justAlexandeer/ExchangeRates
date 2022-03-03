package com.github.justalexandeer.exchangerates.framework.datasource.local.database.mapper

import androidx.room.*
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.entity.CurrencyEntity

@Dao
interface CurrencyEntityDao {

    @Query("SELECT * FROM currency_entity")
    fun getAllCurrencyEntity(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrencyEntity(currencyEntity: CurrencyEntity)

    @Delete
    fun removeCurrencyEntity(currencyEntity: CurrencyEntity)

}