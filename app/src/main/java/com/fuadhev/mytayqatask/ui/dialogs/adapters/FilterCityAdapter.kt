package com.fuadhev.mytayqatask.ui.dialogs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.mytayqatask.common.utils.GenericDiffUtil
import com.fuadhev.mytayqatask.data.local.model.CityEntity
import com.fuadhev.mytayqatask.databinding.ItemFilterOptionBinding

class FilterCityAdapter: ListAdapter<CityEntity, FilterCityAdapter.ViewHolder>(GenericDiffUtil<CityEntity>(
    myItemsTheSame = { oldItem, newItem -> oldItem.countryId == newItem.countryId },
    myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)) {


    inner class ViewHolder(private val binding: ItemFilterOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityEntity) {
            binding.checkBox.text=item.name
            binding.checkBox.isChecked=item.isChecked
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilterOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val item=getItem(position)

    }

}