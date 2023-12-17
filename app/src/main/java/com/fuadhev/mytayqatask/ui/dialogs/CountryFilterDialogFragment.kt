package com.fuadhev.mytayqatask.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.room.util.copy
import com.fuadhev.mytayqatask.common.utils.Extensions.showMessage
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.databinding.CountryDialogBinding
import com.fuadhev.mytayqatask.ui.person.PersonViewModel
import com.fuadhev.mytayqatask.ui.dialogs.adapters.FilterCountryAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFilterDialogFragment : DialogFragment() {

    private var _binding: CountryDialogBinding? = null
    private val binding get() = _binding!!

    private val countryAdapter by lazy {
        FilterCountryAdapter()
    }

    private val viewModel by activityViewModels<PersonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryDialogBinding.inflate(inflater, container, false)

        binding.rvFilterItems.adapter = countryAdapter
        return binding.root
    }

    private fun observes() {
        viewModel.filterState.observe(viewLifecycleOwner) {
            val countries = it.selectedCountries.map { country ->
                country.copy()
            }
            countryAdapter.submitList(countries)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observes()

        binding.btnFilter.setOnClickListener {
            if (countryAdapter.currentList.all { !it.isChecked }){
                requireActivity().showMessage("You must choose at least 1 country",FancyToast.ERROR)
                return@setOnClickListener
            }
            viewModel.updateFilterStateCountries(countryAdapter.currentList)
            dismiss()
        }


        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.filterState.removeObservers(viewLifecycleOwner)
        _binding = null

    }
}