package com.fuadhev.mytayqatask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.fuadhev.mytayqatask.data.local.model.CityEntity

@Dao
interface CityDao {

    @Upsert
    fun insertCity(city: CityEntity):Long


    @Query("SELECT * FROM cities WHERE countryId IN (:countryIds)")
    suspend fun getCitiesByCountryIds(countryIds:List<Int>): List<CityEntity>

}