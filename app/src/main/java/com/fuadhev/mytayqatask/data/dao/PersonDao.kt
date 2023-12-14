package com.fuadhev.mytayqatask.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fuadhev.mytayqatask.data.model.PeopleEntity

@Dao
interface PersonDao {

    @Upsert
    fun insertPerson(person:PeopleEntity):Long

    @Query("SELECT*FROM peoples")
    suspend fun getPeoples():List<PeopleEntity>


    @Query("SELECT * FROM peoples " +
            "INNER JOIN cities ON peoples.cityId = cities.cityId " +
            "INNER JOIN countries ON cities.countryId = countries.countryId " +
            "WHERE countries.countryId IN (:countryIds) AND cities.cityId IN (:cityIds)")
    suspend fun getPeopleFromCertainCountriesAndCities(countryIds: List<Int>,cityIds:List<Int>): List<PeopleEntity>
}