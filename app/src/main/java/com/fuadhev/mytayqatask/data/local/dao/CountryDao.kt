package com.fuadhev.mytayqatask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.data.local.model.PeopleEntity

@Dao
interface CountryDao {

    @Upsert
    suspend fun insertCountry(country: List<CountryEntity>):List<Long>


   @Query("SELECT*FROM countries")
   suspend fun getCountries():List<CountryEntity>



}