package com.thefork.challenge.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thefork.challenge.domain.User
import com.thefork.challenge.search.databinding.ItemUserBinding

interface OnItemClickListenerPaged {
    fun onItemClicked(user: User)
}

class UsersAdapterPaged(
    private val itemClickListener: OnItemClickListenerPaged
) : PagingDataAdapter<User, UsersAdapterPaged.UsersViewHolder>(UsersComparator) {

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        with(holder) {
            getItem(position)?.let { user ->
                binding.lastNameTextView.text = user.lastName
                binding.firstNameTextView.text = user.firstName
                itemView.setOnClickListener {
                    itemClickListener.onItemClicked(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    object UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}
