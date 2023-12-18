package com.fuadhev.mytayqatask.ui.person

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.mytayqatask.common.utils.isOnline
import com.fuadhev.mytayqatask.data.local.PersonDB
import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.data.local.model.PeopleEntity
import com.fuadhev.mytayqatask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: Repository, private val db: PersonDB) :
    ViewModel() {


    private val _peopleData = MutableLiveData<List<PeopleEntity>>(emptyList())
    val peopleData get() = _peopleData

    private val _localDataIsEmpty = MutableLiveData<Boolean>(true)
    val localDataIsEmpty get() = _localDataIsEmpty

    private val _filterState = MutableLiveData(FilterState(emptyList(), emptyList()))
    val filterState get() = _filterState

    fun updateFilterStateCountries(countries: List<CountryEntity>) {
        _filterState.value = _filterState.value!!.copy(
            selectedCountries = countries
        )
        getCitiesFromDB()
    }

    fun updateFilterStateCities(cities: List<CityEntity>) {
        _filterState.value = _filterState.value!!.copy(
            selectedCities = cities
        )
    }

    fun getPeoples() {
        viewModelScope.launch(IO) {
            val data = db.getPersonDao().getPeoples()
            withContext(Main) {
                _peopleData.value = data
                if (data.isNotEmpty()){
                    _localDataIsEmpty.value = false
                }


            }
            getCountriesFromDB()
        }
    }

    fun getPeopleFromCertainCountriesAndCities(countryIds: List<Int>, cityIds: List<Int>) {
        viewModelScope.launch(IO) {
            val peoples =
                db.getPersonDao().getPeopleFromCertainCountriesAndCities(countryIds, cityIds)
            withContext(Main) {
                peopleData.value = peoples
            }

        }
    }

    private fun getCountriesFromDB() {
        viewModelScope.launch(IO) {
            val countries = db.getCountryDao().getCountries()

            withContext(Main) {
                _filterState.value = _filterState.value?.copy(
                    selectedCountries = countries
                )
            }
            getCitiesFromDB()
        }
    }

    private fun getCitiesFromDB() {
        viewModelScope.launch(IO) {
            val countriesIds = _filterState.value?.selectedCountries?.asSequence()?.filter {
                it.isChecked
            }?.map {
                it.countryId
            }?.toList()

            val cities = db.getCityDao().getCitiesByCountryIds(countriesIds ?: emptyList())
            withContext(Main) {
                _filterState.value = _filterState.value?.copy(
                    selectedCities = cities
                )
            }
        }
    }


    fun getCountriesData() {
        viewModelScope.launch(IO) {
            try {
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
                    }

                }
                getPeoples()
            } catch (e: Exception) {
                getPeoples()
            }

        }

    }


}
