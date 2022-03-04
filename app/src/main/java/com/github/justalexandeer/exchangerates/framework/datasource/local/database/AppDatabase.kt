package com.github.justalexandeer.exchangerates.framework.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.entity.CurrencyEntity
import com.github.justalexandeer.exchangerates.framework.datasource.local.database.dao.CurrencyEntityDao

@Database(
    entities = [CurrencyEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyEntityDao
}

const val DATABASE_NAME = "App_Database"
private const val DATABASE_VERSION = 1