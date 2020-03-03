package com.zanvent.mvvm_rxkotlin_sample.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zanvent.mvvm_rxkotlin_sample.data.models.DetailedUser
import com.zanvent.mvvm_rxkotlin_sample.databinding.ItemUserBinding

class UsersAdapter() : ListAdapter<DetailedUser,
        UsersAdapter.ViewHolder>(FileDataDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder constructor(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DetailedUser) {
            binding.item  = item;
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class FileDataDiffCallback : DiffUtil.ItemCallback<DetailedUser>() {
        override fun areItemsTheSame(oldItem: DetailedUser, newItem: DetailedUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailedUser, newItem: DetailedUser): Boolean {
            return oldItem == newItem
        }
    }
}