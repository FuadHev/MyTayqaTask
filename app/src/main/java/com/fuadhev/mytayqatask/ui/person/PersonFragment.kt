package com.fuadhev.mytayqatask.ui.person

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.mytayqatask.common.base.BaseFragment
import com.fuadhev.mytayqatask.common.utils.isOnline
import com.fuadhev.mytayqatask.databinding.FragmentPersonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate) {

    private val viewModel by viewModels<PersonViewModel>()

    private val personAdapter by lazy {
        PersonAdapter()
    }


    override fun observeEvents() {
        viewModel.peopleData.observe(viewLifecycleOwner) {
            personAdapter.submitList(it)
        }


    }

    override fun setupListeners() {
        binding.icCountry.setOnClickListener {
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToCountryFilterDialogFragment())
        }
    }



    override fun onCreateFinish() {


        if (isOnline(requireContext())){
            viewModel.getCountriesData()
        }else{
            viewModel.getPeoples()
        }

        binding.rvPerson.adapter = personAdapter
    }

}