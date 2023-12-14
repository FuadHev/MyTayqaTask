package com.fuadhev.mytayqatask.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuadhev.mytayqatask.R
import com.fuadhev.mytayqatask.common.base.BaseFragment
import com.fuadhev.mytayqatask.data.network.dto.People
import com.fuadhev.mytayqatask.databinding.FragmentPersonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate) {


    private val personAdapter by lazy {
        PersonAdapter()
    }

    override fun observeEvents() {

    }

    override fun onCreateFinish() {
        binding.rvPerson.adapter=personAdapter
    }

}