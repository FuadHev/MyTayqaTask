package com.fuadhev.mytayqatask.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fuadhev.mytayqatask.data.model.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityEntity):Long


    @Query("SELECT * FROM cities WHERE countryId IN (:countryIds)")
    suspend fun getCitiesByCountryIds(countryIds:List<Int>): List<CityEntity>

}