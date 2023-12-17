package com.fuadhev.mytayqatask.ui.person

import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.data.local.model.CountryEntity

data class FilterState(
    val selectedCountries: List<CountryEntity> = emptyList(),
    val selectedCities: List<CityEntity> = emptyList()
)