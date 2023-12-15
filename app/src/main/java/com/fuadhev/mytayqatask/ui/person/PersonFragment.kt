package com.fuadhev.mytayqatask.ui.person

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.mytayqatask.common.base.BaseFragment
import com.fuadhev.mytayqatask.common.utils.isOnline
import com.fuadhev.mytayqatask.databinding.FragmentPersonBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.security.auth.login.LoginException

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate) {

    private val viewModel by viewModels<PersonViewModel>(
        ownerProducer = { requireActivity() }
    )

    private val personAdapter by lazy {
        PersonAdapter()
    }


    override fun observeEvents() {
        viewModel.peopleData.observe(viewLifecycleOwner) {
            personAdapter.submitList(it)
        }
        viewModel.filterState.observe(viewLifecycleOwner) {filterState->
            if (filterState.selectedCityIds.isNotEmpty() && filterState.selectedCountryIds.isNotEmpty()) {
                val cityIds=filterState.selectedCityIds.asSequence().filter {
                    it.isChecked
                }.map { city->
                    city.cityId
                }.toList()
                val countryIds=filterState.selectedCountryIds.asSequence()
                    .filter { it.isChecked }
                    .map {country->
                        country.countryId
                    }.toList()
                viewModel.getPeopleFromCertainCountriesAndCities(countryIds,cityIds)
            }
        }


    }

    override fun setupListeners() {
        binding.icCountry.setOnClickListener {
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToCountryFilterDialogFragment())
        }
        binding.icCity.setOnClickListener {
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToCityFilterDialogFragment())
        }
        binding.swipeRefresh.setOnRefreshListener {
            if (isOnline(requireContext())) {
                viewModel.getCountriesData()
            } else {
                viewModel.getPeoples()
            }
            binding.swipeRefresh.isRefreshing = false

        }
    }


    override fun onCreateFinish() {

        if (isOnline(requireContext())) {
            viewModel.getCountriesData()
        } else {
            viewModel.getPeoples()
        }

        binding.rvPerson.adapter = personAdapter
    }

}