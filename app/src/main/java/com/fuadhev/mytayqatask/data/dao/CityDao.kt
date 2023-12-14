package com.fuadhev.mytayqatask.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.fuadhev.mytayqatask.data.model.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityEntity):Long

}