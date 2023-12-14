package com.fuadhev.mytayqatask.data.network.api

import com.fuadhev.mytayqatask.data.network.model.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface PersonApiService {

    @GET("TayqaTech/getdata")
    suspend fun getCountryData():Response<CountryResponse>

}