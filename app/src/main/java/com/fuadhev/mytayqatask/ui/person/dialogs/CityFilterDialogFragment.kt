package com.fuadhev.mytayqatask.ui.person.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.fuadhev.mytayqatask.databinding.CountryDialogBinding
import com.fuadhev.mytayqatask.ui.person.PersonViewModel
import com.fuadhev.mytayqatask.ui.person.adapters.FilterCityAdapter

class CityFilterDialogFragment : DialogFragment() {
    private var _binding: CountryDialogBinding? = null
    private val binding get() = _binding!!

    private val cityAdapter by lazy {
        FilterCityAdapter()
    }

    private val viewModel by viewModels<PersonViewModel>(ownerProducer = {
        requireActivity()
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun observes() {
        viewModel.filterState.observe(viewLifecycleOwner) {
            cityAdapter.submitList(it.selectedCityIds)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFilterItems.adapter = cityAdapter
        observes()
        binding.btnFilter.setOnClickListener {
          viewModel.updateFilterStateCities(cityAdapter.currentList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}