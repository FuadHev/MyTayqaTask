package com.fuadhev.mytayqatask.ui.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.mytayqatask.common.utils.GenericDiffUtil
import com.fuadhev.mytayqatask.data.model.PeopleEntity
import com.fuadhev.mytayqatask.databinding.ItemPersonBinding

class PersonAdapter : ListAdapter<PeopleEntity, PersonAdapter.NewsViewHolder>(GenericDiffUtil<PeopleEntity>(
    myItemsTheSame = { oldItem, newItem -> oldItem.humanId == newItem.humanId },
    myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)) {


    inner class NewsViewHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleEntity) {
            with(binding) {
                val fullName=item.name+" "+item.surname

                txtFullname.text=fullName

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}