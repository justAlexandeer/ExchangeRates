package com.github.justalexandeer.exchangerates.framework.datasource.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = tableName)
data class CurrencyEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String
)

private const val tableName = "currency_entity"