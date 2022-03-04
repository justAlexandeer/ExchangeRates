package com.github.justalexandeer.exchangerates.framework.datasource.local.database.dao

import androidx.room.*
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.entity.CurrencyEntity

@Dao
interface CurrencyEntityDao {

    @Query("SELECT * FROM currency_entity")
    fun getAllCurrencyEntity(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrencyEntity(currencyEntity: CurrencyEntity)

    @Query("DELETE FROM currency_entity WHERE name = :currencyName")
    fun removeCurrencyEntity(currencyName: String)

}