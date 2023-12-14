package com.fuadhev.mytayqatask.data.network.dto


import com.google.gson.annotations.SerializedName

data class City(
    val cityId: Int,
    val name: String,
    val peopleList: List<People>
)