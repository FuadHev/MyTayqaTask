package com.fuadhev.mytayqatask.data.repository

import com.fuadhev.mytayqatask.data.network.api.PersonApiService
import com.fuadhev.mytayqatask.data.network.model.CountryResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: PersonApiService) {
    suspend fun getCountryData(): Response<CountryResponse> {
        return api.getCountryData()
    }
}