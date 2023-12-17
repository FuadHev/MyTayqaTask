package com.fuadhev.mytayqatask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fuadhev.mytayqatask.data.local.model.PeopleEntity

@Dao
interface PersonDao {

    @Upsert
    suspend fun insertPerson(person: PeopleEntity):Long

    @Query("SELECT*FROM peoples")
    suspend fun getPeoples():List<PeopleEntity>

    @Query("SELECT people.* FROM peoples AS people " +
            "INNER JOIN cities ON people.cityId = cities.cityId " +
            "INNER JOIN countries ON cities.countryId = countries.countryId " +
            "WHERE countries.countryId IN (:countryIds) AND cities.cityId IN (:cityIds)")
    suspend fun getPeopleFromCertainCountriesAndCities(countryIds: List<Int>, cityIds: List<Int>): List<PeopleEntity>


}