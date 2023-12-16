package com.fuadhev.mytayqatask.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.fuadhev.mytayqatask.databinding.CountryDialogBinding
import com.fuadhev.mytayqatask.ui.person.PersonViewModel
import com.fuadhev.mytayqatask.ui.dialogs.adapters.FilterCityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFilterDialogFragment : DialogFragment() {
    private var _binding: CountryDialogBinding? = null
    private val binding get() = _binding!!

    private val cityAdapter by lazy {
        FilterCityAdapter()
    }

    private val viewModel by activityViewModels<PersonViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observes() {
        viewModel.filterState.observe(viewLifecycleOwner) {

            val cities = it.selectedCities.map { city ->
                city.copy()
            }
            cityAdapter.submitList(cities)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFilterItems.adapter = cityAdapter
        observes()
        binding.btnFilter.setOnClickListener {
            viewModel.updateFilterStateCities(cityAdapter.currentList)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}