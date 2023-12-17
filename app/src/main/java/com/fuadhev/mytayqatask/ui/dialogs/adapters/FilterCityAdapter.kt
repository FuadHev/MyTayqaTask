package com.fuadhev.mytayqatask.ui.dialogs.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.mytayqatask.common.utils.GenericDiffUtil
import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.data.local.model.CountryEntity
import com.fuadhev.mytayqatask.databinding.ItemFilterOptionBinding

class FilterCityAdapter: ListAdapter<CityEntity, FilterCityAdapter.ViewHolder>(GenericDiffUtil<CityEntity>(
    myItemsTheSame = { oldItem, newItem -> oldItem.countryId == newItem.countryId },
    myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)) {


    inner class ViewHolder(val binding: ItemFilterOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityEntity) {
            binding.checkBox.text=item.name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilterOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val item=getItem(position)
        holder.binding.checkBox.isChecked=item.isChecked
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            Log.e("TAG", "bind: ${currentList.toString()}", )
        }
    }

}