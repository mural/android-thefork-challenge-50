package com.thefork.challenge.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thefork.challenge.api.UserPreview

class UsersAdapter(
    private val users: List<UserPreview>
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val lastNameView = view.findViewById<TextView>(R.id.last_name_text_view)
        private val firstNameView = view.findViewById<TextView>(R.id.first_name_text_view)

        fun bind(user: UserPreview) {
            lastNameView.text = user.lastName
            firstNameView.text = user.firstName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

}
