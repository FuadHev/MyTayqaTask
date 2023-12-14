package com.fuadhev.mytayqatask.data.network.api

import com.fuadhev.mytayqatask.data.network.dto.PersonDTO
import retrofit2.Response
import retrofit2.http.GET

interface PersonApiService {

    @GET("getdata")
    suspend fun getPersonData():Response<PersonDTO>

}