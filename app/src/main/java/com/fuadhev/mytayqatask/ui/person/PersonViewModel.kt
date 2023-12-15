package com.fuadhev.mytayqatask.ui.person

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.mytayqatask.common.utils.isOnline
import com.fuadhev.mytayqatask.data.local.PersonDB
import com.fuadhev.mytayqatask.data.model.CityEntity
import com.fuadhev.mytayqatask.data.model.CountryEntity
import com.fuadhev.mytayqatask.data.model.PeopleEntity
import com.fuadhev.mytayqatask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(val repo: Repository, val db: PersonDB) : ViewModel() {


    private val _peopleData = MutableLiveData<List<PeopleEntity>>()
    val peopleData get() = _peopleData

    val filterState = MutableLiveData(FilterState(emptyList(), emptyList()))
    fun updateFilterStateCountries(countries: List<CountryEntity>) {
        filterState.value = filterState.value!!.copy(
            selectedCountryIds = countries
        )
        getCitiesFromDB()
    }
    fun updateFilterStateCities(cities: List<CityEntity>) {
        filterState.value = filterState.value!!.copy(
            selectedCityIds = cities
        )
    }
    fun getPeoples() {
        viewModelScope.launch(IO) {
            val data = db.getPersonDao().getPeoples()
            withContext(Main) {
                _peopleData.value = data
            }
            getCountriesFromDB()
        }
    }
    fun getPeopleFromCertainCountriesAndCities(countryIds:List<Int>,cityIds:List<Int>){
        viewModelScope.launch(IO) {
            val peoples=db.getPersonDao().getPeopleFromCertainCountriesAndCities(countryIds,cityIds)
            withContext(Main){
                peopleData.value=peoples
            }

        }
    }
    fun getCountriesFromDB() {
        viewModelScope.launch(IO) {
            val countries = db.getCountryDao().getCountries()

            withContext(Main) {
                filterState.value = filterState.value?.copy(
                    selectedCountryIds = countries
                )
            }
            getCitiesFromDB()
        }
    }

    fun getCitiesFromDB() {
        viewModelScope.launch(IO) {
          val countriesIds=filterState.value!!.selectedCountryIds.asSequence()
                .filter { it.isChecked }
                .map {
                it.countryId
            }.toList()

            val cities = db.getCityDao().getCitiesByCountryIds(countriesIds ?: emptyList())
            withContext(Main) {
                filterState.value = filterState.value?.copy(
                    selectedCityIds = cities
                )
            }
        }
    }


    fun getCountriesData() {
        viewModelScope.launch(IO) {
            val response = repo.getCountryData()
            if (response.isSuccessful) {
                val responseData = response.body()
                val countryDao = db.getCountryDao()
                val peopleDao = db.getPersonDao()
                val cityDao = db.getCityDao()
                responseData?.let {
                    val countries = responseData.countryList
                    val countryEntities = countries.map {
                        CountryEntity(it.countryId, it.name, true)
                    }
                    countryDao.insertCountry(countryEntities)
                    countries.forEach { country ->

                        val cities = country.cityList.map { city ->
                            cityDao.insertCity(
                                CityEntity(
                                    city.cityId,
                                    city.name,
                                    country.countryId
                                )
                            )
                            val people = city.peopleList.map { people ->

                                peopleDao.insertPerson(
                                    PeopleEntity(
                                        people.humanId,
                                        people.name,
                                        people.surname,
                                        city.cityId
                                    )
                                )

                                true
                            }
                        }
                    }
                    getPeoples()

                }

            }
        }


    }

}
