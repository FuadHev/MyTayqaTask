package com.fuadhev.mytayqatask.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity (
    @PrimaryKey
    val countryId: Int,
    val name: String,
    var isChecked:Boolean=true
)
//
//
//@Entity(tableName = "cities",
//    foreignKeys = [ForeignKey(
//        entity = CountryEntity::class,
//        parentColumns = ["countryId"],
//        childColumns = ["countryId"],
//        onDelete = ForeignKey.CASCADE
//    )]
//)
//data class CityEntity(
//    @PrimaryKey
//    val cityId: Int,
//    val name: String,
//    val countryId:Int
//) {
//}
//
//
//@Entity(tableName = "peoples",
//    foreignKeys = [ForeignKey(
//        entity = CityEntity::class,
//        parentColumns = ["cityId"],
//        childColumns = ["cityId"],
//        onDelete = ForeignKey.CASCADE
//    )])
//data class PeopleEntity(
//    @PrimaryKey
//    val humanId: Int,
//    val name: String,
//    val surname: String,
//    val cityId:Int
//)