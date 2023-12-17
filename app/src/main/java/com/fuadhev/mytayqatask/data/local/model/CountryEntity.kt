package com.fuadhev.mytayqatask.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity (
    @PrimaryKey
    val countryId: Int,
    val name: String,
    var isChecked:Boolean
)
