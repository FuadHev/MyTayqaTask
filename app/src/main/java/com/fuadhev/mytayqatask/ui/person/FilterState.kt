package com.fuadhev.mytayqatask.ui.person

import com.fuadhev.mytayqatask.data.model.CityEntity
import com.fuadhev.mytayqatask.data.model.CountryEntity

data class FilterState(
    val selectedCountryIds: List<CountryEntity> = emptyList(),
    val selectedCityIds: List<CityEntity> = emptyList()
)