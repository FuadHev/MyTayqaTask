package com.fuadhev.mytayqatask.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fuadhev.mytayqatask.data.model.CountryEntity
import com.fuadhev.mytayqatask.data.model.PeopleEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(country: List<CountryEntity>):List<Long>

//    @Query("SELECT*FROM peoples")





}