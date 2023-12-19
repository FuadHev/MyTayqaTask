package com.fuadhev.mytayqatask.ui.person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.mytayqatask.data.local.PersonDB
import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.data.local.model.PeopleEntity
import com.fuadhev.mytayqatask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: Repository, private val db: PersonDB) :
    ViewModel() {


    private val _peopleData = MutableLiveData<List<PeopleEntity>>(emptyList())
    val peopleData get() = _peopleData

    private val _filterState = MutableLiveData(FilterState(emptyList(), emptyList()))
    val filterState get() = _filterState


    fun updateFilterStateCountries(countries: List<CountryEntity>) {
        _filterState.value = _filterState.value!!.copy(
            selectedCountries = countries
        )
    }

    fun updateFilterStateCities(cities: List<CityEntity>) {
        _filterState.value = _filterState.value!!.copy(
            selectedCities = cities
        )
    }

    fun getLocalPeoplesData() {
        viewModelScope.launch(IO) {
            val data = db.getPersonDao().getPeoples()
            withContext(Main) {
                if (data.isNotEmpty()) {
                    _peopleData.value = data
                }
            }
            getCountriesCitiesFromLocal()
        }
    }

    fun getFilterData(countryIds: List<Int>, cityIds: List<Int>) {
        viewModelScope.launch(IO) {
            val peoplesData =
                db.getPersonDao().getPeopleFromCertainCountriesAndCities(countryIds, cityIds)
            withContext(Main) {
                if (peoplesData.isNotEmpty()) {
                    peopleData.value = peoplesData
                }
            }
        }
    }

    private fun getCountriesCitiesFromLocal() {
        viewModelScope.launch(IO) {
            val countries = db.getCountryDao().getCountries()

            withContext(Main) {
                _filterState.value = _filterState.value?.copy(
                    selectedCountries = countries
                )
            }
            setFilterCitiesFromLocal(countries)
        }
    }

    fun setFilterCitiesFromLocal(selectedCountries:List<CountryEntity>) {
        viewModelScope.launch(IO) {
            val countriesIds = selectedCountries.asSequence().filter {
                it.isChecked
            }.map {
                it.countryId
            }.toList()

            val cities = db.getCityDao().getCitiesByCountryIds(countriesIds ?: emptyList())
            withContext(Main) {
                _filterState.value = _filterState.value?.copy(
                    selectedCities = cities
                )
            }
        }
    }


    fun getRemoteData() {
        /** Sealed Resource classi istifade ede bilerdim ozum etmemisem sade sekilde try catch ile isletdim
         **/
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
                            CountryEntity(it.countryId, it.name)
                        }
                        countryDao.insertCountry(countryEntities)
                        countries.forEach { country ->
                            country.cityList.forEach { city ->
                                cityDao.insertCity(
                                    CityEntity(
                                        city.cityId,
                                        city.name,
                                        country.countryId
                                    )
                                )
                                city.peopleList.forEach { people ->
                                    peopleDao.insertPerson(
                                        PeopleEntity(
                                            people.humanId,
                                            people.name,
                                            people.surname,
                                            city.cityId
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
                getLocalPeoplesData()
            } catch (e: Exception) {
                getLocalPeoplesData()
            }

        }

    }


}
