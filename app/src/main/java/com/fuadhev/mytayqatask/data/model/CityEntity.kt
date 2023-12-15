package com.fuadhev.mytayqatask.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cities",
    foreignKeys = [ForeignKey(
        entity = CountryEntity::class,
        parentColumns = ["countryId"],
        childColumns = ["countryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CityEntity(
    @PrimaryKey
    val cityId: Int,
    val name: String,
    val countryId:Int,
    var isChecked:Boolean=true
)