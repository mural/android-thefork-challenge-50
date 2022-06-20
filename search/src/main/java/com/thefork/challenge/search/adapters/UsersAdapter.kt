package com.thefork.challenge.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thefork.challenge.domain.User
import com.thefork.challenge.search.databinding.ItemUserBinding

interface OnItemClickListener {
    fun onItemClicked(user: User)
}

class UsersAdapter(
    private val users: List<User>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        with(holder) {
            with(users[position]) {
                binding.lastNameTextView.text = this.lastName
                binding.firstNameTextView.text = this.firstName

                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClicked(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = users.size
}
