package com.fuadhev.mytayqatask.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "peoples",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["cityId"],
        childColumns = ["cityId"],
        onDelete = ForeignKey.CASCADE,
    )])
data class PeopleEntity(
    @PrimaryKey
    val humanId: Int,
    val name: String,
    val surname: String,
    val cityId:Int
)

//,indices =[Index(value = ["cityId"])]