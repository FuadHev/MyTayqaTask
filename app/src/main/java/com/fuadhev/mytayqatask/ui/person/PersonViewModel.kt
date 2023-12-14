package com.fuadhev.mytayqatask.ui.person

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.mytayqatask.data.local.PersonDB
import com.fuadhev.mytayqatask.data.model.CityEntity
import com.fuadhev.mytayqatask.data.model.CountryEntity
import com.fuadhev.mytayqatask.data.model.PeopleEntity
import com.fuadhev.mytayqatask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(val repo: Repository, val db: PersonDB) : ViewModel() {


    private val _peopleData = MutableLiveData<List<PeopleEntity>>()
    val peopleData get() = _peopleData
    fun getPeoples() {
        viewModelScope.launch(IO) {
            val data = db.getPersonDao().getPeoples()
            withContext(Main) {
                _peopleData.value = data
            }

        }
    }

//    fun getby() {
//        viewModelScope.launch(IO) {
//            val data = db.getPersonDao().getPeopleFromCertainCountriesAndCities()
//            Log.e("TAG", "getby: ${data.toString()}")
//        }
//    }


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
                        CountryEntity(it.countryId, it.name)
                    }
                    countryDao.insertCountry(countryEntities)
                    countries.forEach { country ->

                        val cities = country.cityList.map { city ->
                            cityDao.insertCity(CityEntity(city.cityId, city.name, country.countryId))
                           val people = city.peopleList.map { people ->
                                Log.e("for", "country 1ci map ")
                               peopleDao.insertPerson(PeopleEntity(
                                    people.humanId,
                                    people.name,
                                    people.surname,
                                    city.cityId
                                ))

                               true
                            }

                            Log.e("people", "${people.toString()} ")



                        }
                    }
                    getPeoples()
                }

            }
        }


    }

}
//        repo.getPersonData().onEach {
//            when (it) {
//                is Resource.Loading -> {
//                    _personState.value=PersonUiState.Loading
//
//                }
//                is Resource.Success -> {
//                   _personState.value=PersonUiState.SuccessPersonData(it.data?: emptyList())
//                }
//                is Resource.Error -> {
//                    _personState.value=PersonUiState.Error(it.exception)
//                }
//
//            }
//        }.launchIn(viewModelScope)