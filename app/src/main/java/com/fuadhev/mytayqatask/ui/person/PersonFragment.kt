package com.fuadhev.mytayqatask.ui.person

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.mytayqatask.common.base.BaseFragment
import com.fuadhev.mytayqatask.common.utils.Extensions.showMessage
import com.fuadhev.mytayqatask.common.utils.isOnline
import com.fuadhev.mytayqatask.databinding.FragmentPersonBinding
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate) {

    private val viewModel by activityViewModels<PersonViewModel>()

    private val personAdapter by lazy {
        PersonAdapter()
    }

    override fun observeEvents() {
        viewModel.getLocalPeoplesData().observe(viewLifecycleOwner){
            if (it.isEmpty()) {
                getPeoplesData()
            }else{
                personAdapter.submitList(it)
                viewModel.getCountriesCitiesFromLocal()
            }

        }
        viewModel.peopleData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                personAdapter.submitList(it)
            }
        }

        viewModel.filterState.observe(viewLifecycleOwner) { filterState ->
            if (filterState.selectedCities.isNotEmpty() && filterState.selectedCountries.isNotEmpty()) {
                val cityIds = filterState.selectedCities.asSequence().filter {
                    it.isChecked
                }.map { city ->
                    city.cityId
                }.toList()
                val countryIds = filterState.selectedCountries.asSequence()
                    .filter { it.isChecked }
                    .map { country ->
                        country.countryId
                    }.toList()
                viewModel.getFilterData(countryIds, cityIds)
            }
        }

    }

    override fun setupListeners() {

        /** Dialogu 1 fragmente saxlaya bilerdim argument gondererek ona uygun
        quracaqdim sadece ayirmaq indi daha rahat geldi deye bele etdim **/

        binding.icCountry.setOnClickListener {
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToCountryFilterDialogFragment())
        }
        binding.icCity.setOnClickListener {
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToCityFilterDialogFragment())
        }

        binding.swipeRefresh.setOnRefreshListener {
            getPeoplesData()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    private fun getPeoplesData() {
        if (isOnline(requireContext())) {
            viewModel.getRemoteData()
        } else {
            requireActivity().showMessage(
                "No Internet Connection. Local data is Loading",
                FancyToast.INFO
            )
            viewModel.getLocalPeoplesData()
            viewModel.getCountriesCitiesFromLocal()
        }
    }

    override fun onCreateFinish() {
        binding.rvPerson.adapter = personAdapter
    }

}