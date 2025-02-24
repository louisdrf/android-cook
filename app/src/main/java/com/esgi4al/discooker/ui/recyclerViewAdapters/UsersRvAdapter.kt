package com.esgi4al.discooker.ui.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.ui.viewHolders.UserViewHolder

class UsersRvAdapter(
    private val users: List<ListableUser>,
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = this.users[position]

        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${user.username}")
            .into(holder.userProfileImage)

        holder.userName.text = user.username

        holder.userNbCumulatedLikes.text = user.nbLikes.toString()
        holder.userNbCumulatedRecipes.text = user.nbRecipes.toString()
    }

    override fun getItemCount(): Int = users.size
}