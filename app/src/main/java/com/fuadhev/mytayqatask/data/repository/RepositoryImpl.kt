package com.fuadhev.mytayqatask.data.repository

import com.fuadhev.mytayqatask.data.network.api.PersonApiService
import com.fuadhev.mytayqatask.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(api:PersonApiService): Repository  {
    override fun getPersonData() {

    }
}