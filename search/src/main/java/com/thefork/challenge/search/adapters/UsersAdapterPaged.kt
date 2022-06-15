package com.thefork.challenge.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thefork.challenge.api.UserPreview
import com.thefork.challenge.search.R
import com.thefork.challenge.search.databinding.ItemUserBinding

interface OnItemClickListenerPaged {
    fun onItemClicked(user: UserPreview)
}

class UsersAdapterPaged(
    private val itemClickListener: OnItemClickListenerPaged
) : PagingDataAdapter<UserPreview, UsersAdapterPaged.UsersViewHolder>(UsersComparator) {

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.last_name_text_view).text =
            getItem(position)!!.firstName

        with(holder) {
            getItem(position)?.let { user ->
                binding.lastNameTextView.text = user.lastName
                binding.firstNameTextView.text = user.firstName
                this.itemView.setOnClickListener {
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

    object UsersComparator : DiffUtil.ItemCallback<UserPreview>() {
        override fun areItemsTheSame(oldItem: UserPreview, newItem: UserPreview): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserPreview, newItem: UserPreview): Boolean {
            return oldItem == newItem
        }
    }

}
