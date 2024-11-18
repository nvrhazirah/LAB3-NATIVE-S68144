package com.example.recyclerviewsqlitedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Updated constructor to take a List<User> instead of MainActivity
class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // Inflate the user item layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Ensure that user data exists before binding
        if (position >= 0 && position < userList.size) {
            val user = userList[position]
            holder.nameTextView.text = user.name
            holder.ageTextView.text = user.age.toString()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    // ViewHolder class for user item
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val ageTextView: TextView = itemView.findViewById(R.id.tv_age)
    }
}
