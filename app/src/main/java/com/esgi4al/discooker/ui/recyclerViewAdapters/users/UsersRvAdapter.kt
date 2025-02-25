package com.esgi4al.discooker.ui.recyclerViewAdapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.ListableUserViewHolder

class UsersRvAdapter(
    private val users: List<ListableUser>,
    private val userClickHandler: UserItemClickHandler
) : RecyclerView.Adapter<ListableUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListableUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_user, parent, false)
        return ListableUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListableUserViewHolder, position: Int) {
        val user = this.users[position]

        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${user.username}")
            .into(holder.userProfileImage)

        holder.userName.text = user.username

        holder.userNbCumulatedLikes.text = user.nbLikes.toString()
        holder.userNbCumulatedRecipes.text = user.nbRecipes.toString()

        holder.itemView.setOnClickListener {
            userClickHandler.onUserClick(user._id)
        }
    }

    override fun getItemCount(): Int = users.size
}