package com.fuadhev.mytayqatask.data.network.dto


import com.google.gson.annotations.SerializedName

data class Country(
    val cityList: List<City>,
    val countryId: Int,
    val name: String
)