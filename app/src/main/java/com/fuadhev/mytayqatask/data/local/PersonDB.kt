package com.fuadhev.mytayqatask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fuadhev.mytayqatask.data.local.dao.CityDao
import com.fuadhev.mytayqatask.data.local.dao.CountryDao
import com.fuadhev.mytayqatask.data.local.dao.PersonDao
import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.data.local.model.PeopleEntity


@Database(entities = [CountryEntity::class, CityEntity::class, PeopleEntity::class], version = 1, exportSchema = true)
abstract class PersonDB:RoomDatabase() {

    abstract fun getPersonDao(): PersonDao
    abstract fun getCountryDao(): CountryDao
    abstract fun getCityDao(): CityDao
}